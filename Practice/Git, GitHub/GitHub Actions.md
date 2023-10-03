
#### GitHub Actions Workflow 간단  
GitHub Actions 에서 crontab이 정확한 시간주기로 작동하지 않는다는 이야기가 있음!  

```
on:
	pull_request:
		paths:
		paths-ignore:
		
		types: [opened, reopened, synchronize ]
```  
paths, paths-ignore는 브랜치나 tag와 무관한 것이고, 해당 디렉토리 경로에 존재하는 파일이 수정되었을 경우 작업이 수행되어야함을 나타낸다. ignore는 마찬가지로 무시하는 경우다. 예를들자면 frontend, backend가 하나의 저장소에서 별개의 디렉토리로운용될경우, frontend 작업내용의 PR이 발생하였을 경우, backend test를 수행할 필요가 없다.  

type의 경우 위 3가지가 기본값인데, synchronize란, 예를들어 PR이 Open되어있는상태(아직 Merge되지않음)에서 리뷰어가 추가로 수정을 요구했고, 요청자는 추가수정 작업후 commit-push를 더했을경우 발생하는 이벤트라고 볼 수 있다.  

```
name: test11
	...

name: test22
on:
	workflow_run:
		workflows: [ test11 ]
```
전체적으로 아직 검증해보지 않은 모호한 부분 {
	workflow_run 의 경우, 현재 test11작업이 "실행요청을 받을때와 실행이 완료될때" test22작업이 수행되어야 하는것을 의미한다. 이런식의 Chaining을 이어갈수있는데 3개까지만 동작한다고 한다.  
}


**workflow_dispatch**
수동 트리거 기능으로, jenkins의 build with parameter와 유사하다. POST Request를 통해 이벤트를 발생시킬 수 있고, 수동으로 클릭하는 버튼도 사용할 수 있다고 한다.
```
on:
  workflow_dispatch:
    inputs:
      logLevel:
        description: 'Log level'     # 이런 것들은 다 input parameter 이다.
        required: true
        default: 'warning'
      tags:
        description: 'Test scenario tags'
```

Test 자동화 예제  
```
name: Backend-Test

on:
  pull_request:
    branches: [ main ]

permissions:
  contents: read
  checks: write
  pull-requests: write

jobs:
  test:
    runs-on: ubuntu-22.04 

    steps:
      - uses: actions/checkout@v3.6.0

      - name: Set up JDK 11 # JAVA 버전 지정
        uses: actions/setup-java@v3.12.0
        with:
          java-version: '11'
          distribution: 'temurin'

      - name: Grant execute permission for gradlew
        working-directory: ./ci-test
        run: chmod +x ./gradlew

      - name: Test with Gradle 
        working-directory: ./ci-test
        run: ./gradlew test -s # stacktrace 출력

      - name: Publish Unit Test Results # test 실패 시 PR에 코멘트
        uses: mikepenz/action-junit-report@v3.8.0
        if: always()
        with:
          report_paths: ci-test/build/test-results/test/TEST-*.xml
```  
위의 경우 `mikepenz/action-junit-report@v3.8.0` 를 사용하고 있는데, 테스트 결과를 PR에 알려주는 역할. GitHub Actions Market에서 적절히 찾아서 사용하도록 하자.  


```
- name: Gradle Caching
        uses: actions/cache@v3.3.1
        with:
          path: |  
              ~/.gradle/caches
              ~/.gradle/wrapper
          key: ${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle*', '**/gradle-wrapper.properties') }}
          restore-keys: |
              ${{ runner.os }}-gradle-
```  
Gradle SpringBoot Caching 예시.  


---  
#### $ {{ secrets.GITHUN_TOKEN }}

GitHub Actions Workflow 에서 사용할 수 있도록 **자동 생성되는 인증 토큰**.
사용 권한은 workflow가 포함된 repository로 제한된다.
작업이 완료되면 토큰이 만료된다.

---

