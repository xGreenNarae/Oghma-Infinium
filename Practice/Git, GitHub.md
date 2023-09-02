#### git squash
- 여러 개의 이력을 하나로 합칠 때 사용.
- squash commit : 합치고자 하는 커밋의 부모 커밋으로 rebase, 남길 커밋을 제외하고 squash.
- squash & merge : merge 시에 squash 를 사용하는 것. 대략 깔끔해지는 대신, atomic rollback 이 불가능.  

---  

#### git push origin main의 의미  
`git push origin src:dest`의 경우, 로컬의 src브랜치에서 원격 저장소의 dest브랜치로 push 하는것을 의미한다.  
`git push origin main`의 경우, 로컬브랜치 이름이 생략되었기 때문에, 로컬브랜치와 원격저장소의 브랜치이름이 모두 main이라고 가정하고 수행되는 명령어이다.  
따라서 로컬에 main브랜치가 존재하지 않으면 에러가 발생한다.  

---  

#### .gitmodules 서브모듈  
특정 디렉토리 내용을 다른 GitHub의 repository를 참조하도록 만들 수 있다.  
예를들어 비밀번호 정보같은것을 별도의 private repository에 연결시키고 서브모듈로 관리한다면, 상위 repository는 public이더라도 이것을 내려받은 계정이 서브모듈 접근권한이 없다면 서브모듈 부분은 내려받을 수 없다. -> **이것은 결과적으로 좋은 practice는 아닌것으로 보인다.** 비밀번호는 GitHub에서 제공하는 Secret 서비스를 활용하도록 하자.  
maven repository 등 공식 저장소에서 제공하는 외부 의존성 모듈들은 maven, gradle 등으로 관리가 충분하다고 보이지만, 자체적으로 운용하는 repository에서 구현된 모듈들을 관리할때 좋다!  

---  

#### GitHub Project  

모노 레포에서 backend, frontend 디렉토리를 각각 두고 운용할 경우, 하나의 타임라인으로 진행되는 작업으로 보고, 롤백 시 서로의 작업이 같이 롤백되는 것은 지극히 정상이라고 봐야 맞겠다.  

**초기 설정**  
Project Kanban Board 설정.    
Label 설정. Label은 이슈나 PR들의 주제를 한 눈에 구분하기 쉽게 만드는 목적이 큰 듯하다. 너무 다양할 필요는 없어보인다.  
Kanban을 3단계(Backlog - In progress - Done) 으로 운영할 경우, "제안"이 ISSUE로 취급되면 Backlog에 들어가게 된다. 이 경우 아직 논의가 완료되지 않은 제안과 실제 해야할 작업 사이의 구별이 어려워질 수 있으므로, "제안"은 Discussion을 이용하는 것이 좋겠다. 또는 Kanban의 단계를 늘리거나.  
ISSUE TEMPLATE은 Repository Settings에서 설정할 수 있다.  
PR TEMPLATE은 root directory의 `.github` 폴더 아래 `pull_request_template.md` 파일로 직접 만들어야 하는 것 같다.  

**작업 프로세스**  
ISSUE 생성은 Kanban에서 하도록 한다.  
이후 Assignee를 직접 또는 논의를 거친 뒤 할당하고 In progress로 옮긴다.  
Kanban에서 이슈의 체크리스트를 해결해가며 작업을 진행한다. 체크리스트를 commit-message 단위로 계획해두면 커밋단위를 작게 유지하는데에 도움이 될 수도 ?  

---  

#### Software Version  
Major.Minor.Patch+Build  

Major : 기존버전과 호환되지 않으며, API가 변경됨.  
Minor : 기존버전과 호환되며, 새로운 기능이 추가됨.  
Patch : 기존버전과 호환되며, 버그가 수정됨.  

첫 배포 이전에는 0.1.0 으로 시작하도록 하고..  
첫 릴리즈부터 1.0.0 으로 한다.  
1.0.1 등으로 패치가 이루어지고 있는 상태에서 Minor버전이 증가하였을 경우, 1.2.0 으로 한다. 마찬가지로 Major버전이 증가하였을 경우도 동일.  

GitHub 릴리즈 버전이 변경될 경우, Gradle등의 프로젝트관리도구에 명시된 버전도 같이 변경해주도록 유의하자.  

---
 
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

---  

#### PR을 로컬로 가져오기  
일단 브랜치를 하나만들어서 가져올테니 브랜치를 하나만들어서 switch 하고..  
해당 PR이 20번이라면,  
`git pull origin pull/20/head:main`  

---  







	
			