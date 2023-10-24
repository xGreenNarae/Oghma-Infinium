

#### docker build 명령 사용 시 ERROR: failed to read metadata: unexpected end of JSON input  
Windows라면 `%USERPROFILE%.docker\contexts\meta` 폴더를 삭제하고 재시도해볼것.  


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

---

#### Docker Credentials Helper 관련 오류

```
[internal] load metadata for docker.io/library/ ....
Dev Containers CLI: RPC pipe not configured. Message: {"args":["docker-credential-helper","get"],"stdin":"https://index.docker.io/v1/"} ...

```

docker pull 자체가 터지는 오류.

원인은 VSCode dev container로 원격 컨테이너에 접근할 때, 이 플러그인의 기본 설정이 로컬의 자격증명 값을 덮어씌우려는(동기화) 행위인 듯 한데,
플러그인에서 애초에 설정 값을 해제시켜줄 수 있다.

이미 저렇게 되어버렸다면, `~/.docker/..` 경로(사용자 홈 디렉토리 하위)에 `config.json` 등의 파일들에 설정이 덮어씌워져 있을 것. 
이 폴더 자체를 삭제해서 복구했다.

---




