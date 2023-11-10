
2개 이상의 container 를 하나의 설정 파일로 관리

`apt-get install docker-compose-plugin`
`docker compose version`

**기본 명령어**
`compose.yml, docker-compose.yml` 등의 이름으로 설정파일을 참조한다.
`-f` 옵션을 주어 사용자 지정 파일이름을 참조할수있다. (`docker-compose.local.yml` 등)

`docker compose help`

`docker compose up` 컨테이너 생성 및 실행
`-d` 옵션을 주고 백그라운드에서 실행가능
`--scale` 옵션으로 서비스 인스턴스 수를 조절할 수 있다.

`docker compose down` Stop and Remove
특정 컨테이너를 종료하려면 `docker compose down <container-name>` (up도 마찬가지)

`stop`, `start`, `pull`, `run` ....


**docker compose 파일 예시**
```
version: '3'
services:
  nginx:
    image: nginx:latest
    container_name: nginx
    ports:
      - "80:80"
    volumes:
      - ./nginx.conf:/etc/nginx/conf.d/default.conf # 파일 삽입
    depends_on:
      - redis

  flask:
    build:
      context: .
      dockerfile: Dockerfile
    image: flask-image
    container_name: flask
    ports:
      - "8080:8080"
    healthcheck:
      test: ["CMD", "curl", "-f", "http://127.0.0.1:8080/health"]
      interval: 10s
      timeout: 5s
      retries: 3
    depends_on:
      - nginx
      - redis

  redis:
    image: redis
    container_name: redis
    ports:
      - "6379:6379"
```
nginx - flask - redis 구조 이다.

`depends_on` 은 서비스의 시작을 의존하는 설정이다.
여기서는 redis가 제일 먼저 시작되고, 그다음에 nginx 그리고 flask가 시작된다. flask에 2개를 써둔 이유는 여러 개를 명시 할 수 있다는 것의 예제.

`healthcheck` 부분은 해당 컨테이너 내부에서 curl등의 명령어를 실행하여(Dockerfile등으로 빌드할때 추가로 curl을 설치 해줘야 작동한다.) 말 그대로.. 헬스체크를 하는것인데
`docker compose ps -a`, `docker ps -a` 등의 결과로 STATUS필드에 healthy, unhealthy 등의 상태를 보여준다.
jenkins 등으로 읽어가서 사용하자.


`.env` 파일로 환경변수 값을 사용할 수 있다.
