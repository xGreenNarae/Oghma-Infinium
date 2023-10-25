
#### SSH
`ssh -i file_path userName@domain`  
포트 명시 `ssh root@192.168.0.1 -p 5000`  
Windows10 에서는 OpenSSH 앱을 설치하여 SSH 를 간단히 사용할 수 있다.  
윈도우 설정 - 앱 - 선택적기능  

SSH 종료  
`~.`  

**SSH Key Gen**  
OpenSSH 서버 설치  
`sudo apt -y install openssh-server`  
서버 상태 확인  
`systemctl status ssh`  
서버 시작  
`systemctl start ssh`, `restart..`  
중지 `stop`, 삭제 `purge` `openssh-server`  

WSL환경 같은경우 `systemctl` 명령어를 사용하기 어려운 상황이 있는데.. `service <service_name> <command>` 로 대체가능하다.  

설치하고 나면 이것이 가능..  
`ssh-keygen -t rsa`  
`~/.ssh/` 경로에 공개키-개인키쌍이 생긴다.  

SSH키를 접속하고자 하는 서버에 복사한다. (서버에 root가 아닌 사용자 계정을 만들어두도록 하자) 
`ssh-copy-id -p <PORT> <user>@<IP_ADDRESS>`  

SSH는 공개키-개인키 방식을 사용한 클라이언트-서버 통신이다.  
**두 호스트 모두 ssh server가 작동 중 이어야 하며**, 서버의 공개키가 클라이언트에게 존재해야 한다.  

---  
