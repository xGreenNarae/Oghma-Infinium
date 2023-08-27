### Docker  
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