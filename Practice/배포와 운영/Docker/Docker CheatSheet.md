컨테이너들은 프로세스 마냥 호스트 자원을 사용 가능한 최대치만큼 써먹는게 아니라, 각자에게 할당된 만큼만 사용한다. 즉 서로 경쟁하는 구도가 아니다.

docker container 의 기본 게이트웨이는 `172.17.0.1` 이다.  

---

ubuntu docker 설치  
`apt-get install apt-transport-https ca-certificates curl gnupg-agent software-properties-common`  
`curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -`  
`add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" `  
`apt-get install docker-ce docker-ce-cli containerd.io`  


명령어들  

docker 그룹에 사용자 추가(재 로그인 해야함)
`usermod -aG docker <username>`

기본
`docker images`  설치된 이미지 목록 확인
`docker rmi imageName`  이미지 삭제

`docker container ps -a`  실행중인 컨테이너 확인
`docker rm containerName`  컨테이너 삭제(stop을 먼저해야함)
`docker start` , `docker stop` , `docker restart`  컨테이너 실행, 중지 관련

MySQL 실행 예제  
`docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=<password> -d -p 3306:3306 mysql:latest`  
-p : 포트바인딩. 외부포트 : 내부포트  
-d : 백그라운드실행  
-it : 대화형으로 사용하기 위함  
-e : 컨테이너환경변수설정. Dockerfile ENV도 덮어씀  

-v : 볼륨마운트. `외부디렉토리:내부디렉토리`  
-w : WORKDIR 덮어쓰기. 
--privileged : 시스템리소스 접근, 커널 사용 등 ..  
--network=host : 네트워크모드를 호스트로 설정  

`docker exec -it containerName /bin/bash`  

`docker build -t imageName .`  

이미지 파일 만들기
`docker save 이미지명 > 파일명.tar`  
`docker load < 저장된파일명`  

NONE 이미지 삭제 (dangling) 
`docker system prune` 을 사용하도록 하자!
아래와 같은 방법을 쓸 수도 있으나 굳이 그럴 필요가 없다.
`docker rmi $(docker images -f "dangling=true" -q)`  

**이름변경**
- 컨테이너이름변경: `docker rename <old_name> <new_name>`  
- 이미지 이름변경 : 
	- `docker image tag <이전 tag> <새 tag>`
	- `docker rmi <이전 tag>`

컨테이너 진입, 빠져나오기  
`docker attach 컨테이너이름`  
Ctrl+D , exit 하면 컨테이너 종료,  
Ctrl + P + Q 하면 종료없이나옴.  
bash를 실행시키고 싶은거라면 `docekr exec -it <container_name> /bin/bash` 를 사용하도록하자.

컨테이너 안팎으로 파일/폴더 복사. -r 같은거 없어도 폴더면 알아서 폴더복사됨.  
`docker cp CONTAINER:<src_path> <dst_path>`  
`docker cp <src_path> container:<dst_path>`  

컨테이너정보확인 (네트워크 등)  
`docker container inspect <container_name>`  

실행중인 컨테이너 자원 할당 정보 확인
`docker stats`

kill all containers :
```
docker stop $(docker ps -a -q) && \
docker rm $(docker ps -a -q)
```

컨테이너가 시작이 안되고 터져버렸다면? 로그를 본다.  
`docker logs containerName`  

컨테이너에게 할당된 IP 주소 확인  
`docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' <container_name_or_id>`  

---  


