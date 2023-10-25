이름 뜻은 answer + able 느낌이라고 함.
분류는 Configuration Management tool 정도 되겠다.
대안 기술로 **Puppet**이 있는데 이쪽은 오래된 시장 점유율이 큰 제품이고, Ansible과 달리 Agent가 필요하고 구문 실행 이전에 검사가 가능하고.. 아무튼 더 무겁고 풍부한 기술인 듯 함.

#### 환경 설정 자동화 도구 Ansible 기본 개념 및 사용법  
클라이언트-서버 구조이지만  
agent가 없는 시스템으로, ssh연결을 사용한다.  

Ansible서버가 될 호스트를 "컨트롤 노드", 클라이언트를 "매니지드 노드" 라고 표현하겠음.  

**인벤토리**  
관리하고 있는 서버(매니지드 노드) 목록을 가진 파일  

**플레이북**  
구체적으로 실행하게 될 내용들  


매니지드 노드에 openssh-server를 설정하고..  [참고](./SSH)
컨트롤 노드의 ssh 공개키를 배포한다.(ssh-copy-id 등)  

컨트롤 노드에 ansible을 설치한다.  
`apt-get install ansible`  

인벤토리를 작성해본다. `.ini`파일 형식으로 만들어도 되고 yml도 가능한 듯 하다.    

인벤토리 예시(ini파일 형식)  
파일명 inventory 
```
[server]
172.17.0.3
example.com # 도메인이 없으면 당연히 없어도 됨

# 포트, 계정(중요!) 명시하는 법
172.17.0.1:20001  # 포트명시 1번방법
172.17.0.1 ansible_ssh_port=20002 # 포트명시 2번방법 둘중 아무거나.
172.17.0.2:20001 ansible_user=user # 계정 이름 명시 방법

```  

ping 을 실행해 볼 수 있다.  
```
# ping 실행 
ansible server -i inventory -m ping

# 유저명을 주고 실행 
ansible server -i inventory -m ping -u ubuntu
```

ansible.cfg 구성파일을 설정할 수 있다.  

Ansible 의 구성파일의 우선순위  
1. Ansible_config 환경변수로 지정한 파일  
2. 현재 디렉터리의 ansible.cfg (추천한다고함)  
3. 홈 디렉터리의 ~/.ansible.cfg  
4. /etc/ansible/ansible.cfg  

ansible.cfg 예시  
```
[defaults] 
inventory = ./inventory # 사용할 인벤토리파일경로. 파일또는 여러개의인벤토리 파일이있는 디렉토리 가능
remote_user = root # 매니지드 노드에 연결할 사용자 이름
ask_pass = false # default값인데, ssh연결시 암호사용안함을 의미. 매니지드노드가 비밀번호입력을 요구하게 설정되어있다면 이렇게 하면 당연히 입력할 기회조차 주어지지 않아서 안된다고 한다.   

[privilege_escalation] # 매니지드노드에서 ansible이 권한 에스컬레이션을 수행하는 ..
become = true # 다른유저(권한)로 변경하여 행동할지 여부라고.. default는 false. playbook에서 설정을 덮어쓸수있다고함.
become_method = su # root전환방법 sudo를쓸지 su를쓸지 등
become_user = root # 어떤유저로 전환할것인가
become_ask_pass = false # 전환시 암호요청
```  

playbook 예시  
```
$vim nginx-playbook-test.yaml

---
- hosts: server

  tasks:
  - name: Install nginx latest version
    apt:
     name: nginx
     state: latest
```  

실행하기 전에 문법체크  
`ansible-playbook -i inventory nginx-playbook-test.yaml --syntax-check`  

플레이북 실행  
`ansible-playbook -i inventory nginx-playbook-test.yaml`  
 
 
---  
