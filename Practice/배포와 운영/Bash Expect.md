
#### Expect: 대화형 입력 자동화

expect가 설치되어 있어야 함.
`apt-get install expect` 

```expect-test.sh
#!/usr/bin/expect

PWD="0000"

# login to root
expect << EOF # 아래부터 큰따옴표를 \" 처럼 이스케이프하지 않아도 됨.(Heredoc)
spawn su root
expect "Password:"
send "$PWD\r"
expect "#"

# 사용자 확인
send "whoami\r"
expect "#"

# 컨테이너 확인
send "docker container ps -a\r"
expect "#"

# 컨테이너 접속
send "docker exec -it jenkins /bin/bash\r"
expect "#"
  
send "ls -al\r" # 마지막에 엔터(\r) 꼭 넣어줄것.
expect "#" # 항상 명령어 실행이후 쉘을 expect 해줘야 결과출력을 볼수있음.
  
EOF
```
ssh 연결도 자동화 할수 있고,

예를들어 윈도우라면 .bat 파일로 ssh 연결부분만 작성해주도록 한다. (완전 자동화)
```batch
@echo off

ssh -i ./rsa-pem-key.pem username@domain.com < ./expect-test.sh
```
