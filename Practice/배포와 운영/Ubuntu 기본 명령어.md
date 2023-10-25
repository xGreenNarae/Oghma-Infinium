 
#### 유저, 권한 

**새로운 사용자 추가**
`sudo adduser <username>` 
이후 패스워드 입력

**그룹에 사용자추가**
`usermod -aG 그룹이름 사용자이름`

`-rwxrwxrwx` 
User(소유자), Group, Others(나머지)

**권한 변경**
1. `chmod ugo+rw test` 
test라는 이름의 파일 또는 디렉토리에 user, group, others에게 rw권한 추가

2. `chmod o-w test` test에 others에 w권한 제거
3. 
4. `chmod -R g+x test`
test라는 디렉토리와 그 하위 모든디렉토리 및 파일의 group에 x권한 추가

**등록된 사용자 목록**
`grep /bin/bash /etc/passwd`

---

#### 백그라운드실행
`nohup node nodejs_test.js &`  
nohup : 쉘종료, 사용자로그아웃 등에도 계속실행상태  
& : 백그라운드실행  
#### 환경변수  
1. 터미널을 다시시작하면 설정값이 사라지는 방법  
`export PATH=$PATH:/home/username/abc`  

2. `~/.bashrc` 를 수정하여 위 명령어를 마지막에 추가해두면 터미널이 켜질때마다 자동실행된다.  

3. 시스템변수 수정  
`/etc/environment`  

#### 압축파일 관련  
apt-get install gzip  
gzip -d {압축 파일명}.gz  
tar -xvf test.tar  

#### Alias
`alias python='python3'`  
`alias 이걸치면='실제로는이렇게해라'`  

---
#### 디스크 용량 확인 

`df -h` 디스크 사용량 확인 (파일시스템의 총 공간 및 사용가능한 공간)
`du -sh *` 현재 경로에서 하위 디렉토리들과 파일들 용량 확인
`du -sh ./` 현재 경로가 사용중인 사이즈 총 합
`du -sh * | sort -rh | head -n N` 상위 사이즈 N개만 확인한다.

`df`는 할당된 크기, `du`는 실제 사용 중인 크기를 보여준다.

---

#### 메모리 확인

`cat /proc/meminfo`

`free` 여유분
`free -m` mb단위


---
#### 접속 로그 확인
`last -f /var/log/wtmp`

---
