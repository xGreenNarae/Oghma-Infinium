
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
