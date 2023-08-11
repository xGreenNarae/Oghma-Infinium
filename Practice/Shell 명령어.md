### Ubuntu 기본적인 것들  

#### 그룹에 사용자추가
`usermod -aG 그룹이름 사용자이름`

#### 백그라운드실행
`nohup node nodejs_test.js &`  
nohup : 쉘종료, 사용자로그아웃 등에도 계속실행상태  
& : 백그라운드실행  

#### 환경변수  
터미널을 다시시작하면 설정값이 사라지는 방법  
`export PATH=$PATH:/home/username/abc`  

`~/.bashrc` 를 수정하여 위 명령어를 마지막에 추가해두면 터미널이 켜질때마다 자동실행된다.  

시스템변수 수정  
`/etc/environment`  

#### 압축파일 관련  
apt-get install gzip  
gzip -d {압축 파일명}.gz  
tar -xvf test.tar  

#### Alias
`alias python='python3'`  
`alias 이걸치면='실제로는이렇게해라'`  

#### crontab  
crontab -e 설정  
crontab -l 목록보기  
crontab -r 제거  

예시  
db_backup  
매일 0시 0분 backup.sh 파일 실행  
0 0 * * * /root/database_backup/backup.sh  

30분 마다 실행  
*/30 * * * * /root/database_backup/backup.sh  

매주 월요일 새벽 2시에 실행  
0 02 * * 1 /root/database_backup/backup.sh  

매월 1일 새벽 1시에 실행  
0 01 1 * * /root/database_backup/backup.sh  

---  

#### SSH
`ssh -i file_path userName@domain`  
포트 명시 `ssh root@192.168.0.1 -p 5000`  
Windows10 에서는 OpenSSH 앱을 설치하여 SSH 를 간단히 사용할 수 있다.  
윈도우 설정 - 앱 - 선택적기능  

SSH 종료  
`~.`  

---  

#### Docker  
docker container 의 기본 게이트웨이는 `172.17.0.1` 이다.  

`docker images`  
`docker rmi imageName`  

`docker container ps -a`  
`docker rm containerName`  
`docker start` , `docker stop` , `docker restart`  

MySQL 실행 예제  
`docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=<password> -d -p 3306:3306 mysql:latest`  
-p : 포트바인딩. 외부포트 : 내부포트  
-d : 백그라운드실행  
-it : 대화형으로 사용하기 위함  
-e : 컨테이너환경변수설정. Dockerfile ENV도 덮어씀  

-v : 볼륨마운트. 외부디렉토리:내부디렉토리  
-w : WORKDIR 덮어쓰기. 
--privileged : 시스템리소스 접근, 커널 사용 등 ..  
--network=host : 네트워크모드를 호스트로 설정  

`docker exec -it containerName /bin/bash`  

`docker build -t imageName .`  

 이미지 파일 만들기
`docker save 이미지명 > 파일명.tar`  
`docker load < 저장된파일명`  

NONE 이미지삭제  
`docker rmi $(docker images -f "dangling=true" -q)`  

이름변경
컨테이너이름변경: `docker rename <old_name> <new_name>`  
이미지 이름변경 : 
	`docker image tag <이전 tag> <새 tag>`
	`docker rmi <이전 tag>`

컨테이너 진입, 빠져나오기  
`docker attach 컨테이너이름`  
Ctrl+D , exit 하면 컨테이너 종료,  
Ctrl + P + Q 하면 종료없이나옴.  

컨테이너 안팎으로 파일/폴더 복사. -r 같은거 없어도 폴더면 알아서 폴더복사됨.  
`docker cp CONTAINER:<src_path> <dst_path>`  
`docker cp <src_path> container:<dst_path>`  

컨테이너정보확인 (네트워크 등)  
`docker container inspect <container_name>`  

kill all containers :
```
docker stop $(docker ps -a -q) && \
docker rm $(docker ps -a -q)
```

컨테이너가 시작이 안되고 터져버렸다면? 로그를 본다.  
`docker logs containerName`  

---  

#### SCP (Secure Copy)  
`scp 파일명 계정명@원격서버IP:<파일이 저장될 경로>`  

예시  
(EC2) `scp -i /path/key-pair-name.pem /path/my-file.txt ec2-user@instance-public-dns-name:path/`

로컬->원격 (push)
`scp -P 포트 로컬경로/파일명 계정@서버주소:원격경로/파일명`

원격->로컬 (pull)
`scp -P 포트 계정@서버주소:원격경로/파일명 로컬경로/파일명`

-r 옵션주면 폴더도 가능.(폴더내용물이아닌 그 폴더째로 옮겨감)

---  

#### Nginx  

비활성화 된 설정들  
`/etc/nginx/sites-available/default`  

활성화된 설정은 이곳에  
`/etc/nginx/sites-enabled/default` 
여기에 ln -s 심볼릭링크로 위 경로의 .conf 파일을 연결한다.  
설정이끝나면 nginx 재시작.  

`sudo service nginx start` # nginx 실행  
`sudo service nginx restart` #nginx 셧다운 후 재실행  
`sudo servcie nginx reload` #수정된 설정 파일 적용하여 nginx 실행  
`sudo service nginx stop` #nginx 중지  
`nginx -t` #nginx 설정파일의 문법이 올바른지 확인  

---  

#### Jenkins  
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

appleboy/jenkins-action 을 사용하면 원격에서 쉽게 트리거할수있다.
`curl -X POST http://192.168.0.1:9000/job/AppName/build --user greennarae:11087e98dece7fb5a3516735899ec0e910`
API Token 은 jenkins-profile-API Token 에 있는것을 사용한다.


pipeline 스크립트 예제
pipeline {
	agent any
	
	environment {
		GIT_URL = "https://ghp_ABCDEFGABCDEFG@github.com/GreenNarae/SomeRepository.git"
	}


	stages {
		stage('Pull') {
			steps {
				git url: "${GIT_URL}", branch: "main", poll: true, changelog: true
			}
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

---  


















