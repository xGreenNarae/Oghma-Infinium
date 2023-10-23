
#### Jenkins 를 다루는 맥락
  - 머신의 최상위 레벨에 JDK 와 jenkins 등을 설치하는 것이 지저분하다고 느낄 수 있으므로, 컨테이너에 띄우고자 한다면, Jenkins 컨테이너 내부에서 머신의 최상위 레벨에 있는 Docker 컨텍스트를 조작할 수 있어야한다.
    - 이것을 위해서는 Docker outside of Docker.. 라는 식으로 불리는 소켓바인딩 기술을 사용하라고 권장되는 듯.
    - Jenkins 컨테이너 내부에서도 Docker CLI 는 사용할 수 있어야한다. 지금 시점에서는 [docker.io](http://docker.io) 를 설치하는것으로 해결보았다.
  - 이후의 작업에서 Jenkins 의 공식문서는 충분히 신뢰할 수 있는 듯.
  - 일본 프로그래머가 만들었다고 함.  
  
**Practice**  
도커로 젠킨스를 띄울시, 호스트 레벨의 도커와 소켓으로 바인딩하여 사용할수있다. `Docker outside of Docker` 라고 한다.
```
docker run -d --name jenkins --restart=on-failure \
-p 9000:8080 \
-v /var/jenkins_home:/var/jenkins_home \
-v /var/run/docker.sock:/var/run/docker.sock \
-e TZ=Asia/Seoul \
-u root \
jenkins/jenkins
```

이 경우, 젠킨스 컨테이너에서도 docker cli 는 필요하다.(docker.io 를 설치하여 해결가능)

`appleboy/jenkins-action 을 사용하면 원격에서 쉽게 트리거할수있다.` <- 아래와 같이 url의 parameter에 key를 담아 트리거하는 방식이라면 사용하지 않아도 문제없다.
`curl -X POST http://192.168.0.1:9000/job/AppName/build --user greennarae:11087e98dece7fb5a3516735899ec0e910`
API Token 은 jenkins-profile-API Token 에 있는것을 사용한다.

작업 생성 시, `Free Style Project`가 아닌, `Pipeline` 을 사용하도록 하자. 자유롭게 스크립트를 작성할 수 있다.

pipeline 스크립트 예제
pipeline {
	agent any
	
	environment {
		GIT_URL = "https://ghp_ABCDEFGABCDEFG@github.com/GreenNarae/SomeRepository.git"
	} // 변수. $로 가져다 쓰고, 나중에 다른 값으로 대입도 가능.
	// 주의할점, Groovy에서는 큰따옴표로 감싸야 템플릿 문자열이 적용된다!!"


	stages {
		stage('Pull') {
			steps {
				git url: "${GIT_URL}", branch: "main", poll: true, changelog: true
			} // poll은 주기적으로 폴링하는 것. 기본값이 true
		}
		
		
		stage('Build') {
			steps {
				sh 'docker build -t Name .'
			}
		}
		
		stage('Deploy') {
			steps{
				sh 'docker ps -q --filter name=Name | grep -q . && docker stop Name && docker rm Name'
				sh 'docker run --name Name -d -p 8000:8000 -it --privileged --network=host ImageName'
			}
		}

	   stage('Finish') {
			steps{
				sh 'docker images -qf dangling=true | xargs -I{} docker rmi {}'
			}
		}
	}
}

보통 git clone 을 받으면, `<repo_name>` 폴더가 생기고 하위에 파일들이 받아진다.
jenkins git script는, `/var/jenkins_home/workspace/<job_name>` 하위에 `repo_name` 폴더 없이파일들이 생성되므로, **편하게 절대경로를 사용하자**. (sh는 임시폴더가 생성되면서 사용됨..)

**디렉토리를 변경할 때 주의할 점**
다음과 같은 구문을 사용하는 것이 권장된다.
```
stage('test') {
            steps {
              dir("dirname") { // 큰따옴표를 사용함에 주의할것. Groovy 템플릿문자열
                  sh'ls -la'
              }
            }
        }
```

sh 를 사용하고 싶다면, 이런식으로 쓸것. (쉘을 실행하는 주체가 sh 마다 정해진 곳에서 실행되는 듯 하다.)
```
sh script:'''
          #!/bin/bash
          echo "This is start $(pwd)"
          mkdir hello
          cd ./hello
          echo "This is $(pwd)"
        '''
```

다음은 잘못이다.
```
sh "cd ${PROJECT_PATH}" // 지금은 여기로 이동하긴 했으나,
sh "cp ${DOCKER_FILE} ." // 새롭게 쉘이 켜진다거나로.. 다시 경로가 복구된다고 봐야 한다.
```


---  






