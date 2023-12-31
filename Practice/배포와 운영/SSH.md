
**이론**

공개 키와 개인 키 기반 인증을 사용할건데, 접속을 받아들이는 쪽에서 ssh server가 작동 중 이어야 한다.
일단 클라이언트에서 서버에게 자신의 공개키를 배포하고 나면, 자유롭게 접속할 수 있다.

헷갈릴 수 있는 부분은 "최초 인증" 과정인데, 서버에서 ssh server를 오픈하고 나서 모든 불특정 다수의 클라이언트가 접속하지 못하게 하기 위해서... 클라이언트가 자신의 공개키를 서버에 등록하기 위해서 최초에는 서버에서 만들어둔 계정의 **비밀번호** 인증을 사용해야 한다.

---

**practice**

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
공개키를 배포 할 때, 유의할 점: **클라이언트에서 서버로 복사**해야한다. 즉, Ansible같은 경우 Host에서 Managed노드로 접속하려는 것이기 때문에 Host에서 `ssh-copy-id`를 수행해야 한다.
  
---  
