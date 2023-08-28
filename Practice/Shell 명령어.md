### Ubuntu 기본적인 것들  

#### 그룹에 사용자추가
`usermod -aG 그룹이름 사용자이름`

#### 백그라운드실행
`nohup node nodejs_test.js &`  
nohup : 쉘종료, 사용자로그아웃 등에도 계속실행상태  
& : 백그라운드실행  

#### 환경변수  
터미널을 다시시작하면 설정값이 사라지는 방법  
`export PATH=$PATH:/home/username/abc`  

`~/.bashrc` 를 수정하여 위 명령어를 마지막에 추가해두면 터미널이 켜질때마다 자동실행된다.  

시스템변수 수정  
`/etc/environment`  

#### 압축파일 관련  
apt-get install gzip  
gzip -d {압축 파일명}.gz  
tar -xvf test.tar  

#### Alias
`alias python='python3'`  
`alias 이걸치면='실제로는이렇게해라'`  

#### crontab  
crontab -e 설정  
crontab -l 목록보기  
crontab -r 제거  

예시  
db_backup  
매일 0시 0분 backup.sh 파일 실행  
0 0 * * * /root/database_backup/backup.sh  

30분 마다 실행  
*/30 * * * * /root/database_backup/backup.sh  

매주 월요일 새벽 2시에 실행  
0 02 * * 1 /root/database_backup/backup.sh  

매월 1일 새벽 1시에 실행  
0 01 1 * * /root/database_backup/backup.sh  

---  

#### SSH
`ssh -i file_path userName@domain`  
포트 명시 `ssh root@192.168.0.1 -p 5000`  
Windows10 에서는 OpenSSH 앱을 설치하여 SSH 를 간단히 사용할 수 있다.  
윈도우 설정 - 앱 - 선택적기능  

SSH 종료  
`~.`  

---  



#### SCP (Secure Copy)  
`scp 파일명 계정명@원격서버IP:<파일이 저장될 경로>`  

예시  
(EC2) `scp -i /path/key-pair-name.pem /path/my-file.txt ec2-user@instance-public-dns-name:path/`

로컬->원격 (push)
`scp -P 포트 로컬경로/파일명 계정@서버주소:원격경로/파일명`

원격->로컬 (pull)
`scp -P 포트 계정@서버주소:원격경로/파일명 로컬경로/파일명`

-r 옵션주면 폴더도 가능.(폴더내용물이아닌 그 폴더째로 옮겨감)

---  

#### Nginx  

비활성화 된 설정들  
`/etc/nginx/sites-available/default`  

활성화된 설정은 이곳에  
`/etc/nginx/sites-enabled/default` 
여기에 ln -s 심볼릭링크로 위 경로의 .conf 파일을 연결한다.  
설정이끝나면 nginx 재시작.  

`sudo service nginx start` # nginx 실행  
`sudo service nginx restart` #nginx 셧다운 후 재실행  
`sudo servcie nginx reload` #수정된 설정 파일 적용하여 nginx 실행  
`sudo service nginx stop` #nginx 중지  
`nginx -t` #nginx 설정파일의 문법이 올바른지 확인  

---  













