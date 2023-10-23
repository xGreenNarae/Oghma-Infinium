### Docker Cheat Sheet  
docker container 의 기본 게이트웨이는 `172.17.0.1` 이다.  

ubuntu docker 설치  
`apt-get install apt-transport-https ca-certificates curl gnupg-agent software-properties-common`  
`curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -`  
`add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" `  
`apt-get install docker-ce docker-ce-cli containerd.io`  


명령어들  

docker 그룹에 사용자 추가(재 로그인 해야함)
`usermod -aG docker <username>`


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

-v : 볼륨마운트. `외부디렉토리:내부디렉토리`  
-w : WORKDIR 덮어쓰기. 
--privileged : 시스템리소스 접근, 커널 사용 등 ..  
--network=host : 네트워크모드를 호스트로 설정  

`docker exec -it containerName /bin/bash`  

`docker build -t imageName .`  

 이미지 파일 만들기
`docker save 이미지명 > 파일명.tar`  
`docker load < 저장된파일명`  

NONE 이미지삭제  
`docker system prune` 을 사용하도록 하자!
아래와 같은 방법을 쓸 수도 있으나 굳이 그럴 필요가 없다.
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

컨테이너에게 할당된 IP 주소 확인  
`docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' <container_name_or_id>`  

---  

#### docker build 명령 사용 시 ERROR: failed to read metadata: unexpected end of JSON input  
Windows라면 `%USERPROFILE%.docker\contexts\meta` 폴더를 삭제하고 재시도해볼것.  


---  

#### Dockerfile 문법에 대해서

`WORKDIR` 
실행 중인 컨테이너 환경에서 이동할 경로(cd)
디렉토리가 존재하지 않는 경우, **생성한다**

```
RUN ["<커맨드>", "<파라미터1>", "<파라미터2>"]
RUN <전체 커맨드>
```
커맨드 실행

아래 두 명령어는 `RUN` 과 사용법은 같다.
`ENTRYPOINT` 이걸로 실행된 프로세스는 컨테이너와 수명주기를 같이한다.
`CMD`
`RUN` 은 빌드 시 수행된다. 하지만 `CMD`는 빌드가 아닌 **컨테이너 실행** 시 수행된다. 또한 인자를 `docker run` 명령어에 넣게 되면 덮어 씌워진다.(무시됨)

`EXPOSE` 내부에서 노출하는 PORT. 외부와 바인딩하는 포트는 `docker run` 에서 담아야 함.

`COPY <src> <dest>`
src는 Dockerfile이 위치한 호스트 디렉토리의 데이터, dest는 컨테이너 내부의 경로(상대경로일 시에 WORKDIR 고려)
```
FROM image:tag AS builderName // 여기서 지정한 빌드 단계의 이름을.
...

FROM ...

COPY --from=builderName . . // 이렇게 사용할 수 있다.
```



`ADD` 의 경우 `COPY` 보다 더 다양한 파일(압축파일, 네트워크 상의 파일 등..) 을 다룰 수 있다고 한다.
tar gz 같은 파일은 모두 압축해제하여 추가한다. 
로컬 파일 대신 wget명령어를 지원하여 추가 할 수 있다. 
`ADD hom* /work/dir/`
컨테이너 '/work/dir'주소에 현재 호스트 pwd안에 hom으로 시작하는 파일 모두 추가 

`ENV key=value`
`ENV key value`
환경변수 설정. 빌드 시에만 사용하는 것이 아닌, 컨테이너 내부에도 적용된다.

`ARG number`
인자를 넘겨받는 것.
`ARG number=35`
위의 경우 35가 기본값.
`docker build --build-arg number=100 .` 과 같이 사용.
`CMD start.sh -h 127.0.0.1 -p ${number}` 로 사용 가능.

---

#### var/lib/docker/overlay2 가 디스크를 가득 잡아먹고 있을 때
`docker system prune -all` 로 해결.
임시 파일들이 쌓이는 경로라고 한다. 
설정파일을 변경하여, 이 경로를 다른 경로로 잡아두고 사용할 수도 있다.

위 명령어를 입력하면 yes를 추가로 입력해줘야 하는데,
`docker system prune -af` 로 간단히 하나의 명령어로 줄일 수 있다.

**디스크 관련 명령어**
`docker system df` Images, Containers, Local Volumes, Build Cache 별로 사용중인 사이즈를 확인할 수 있다.
`docker system df -v` 각 컨테이너 별로 사용중인 사이즈가 나온다.