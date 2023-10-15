
`crontab -e` 설정  
`crontab -l` 목록보기  
`crontab -r` 제거  

예시  
db_backup  
매일 0시 0분 backup.sh 파일 실행  
`0 0 * * * /root/database_backup/backup.sh`  

30분 마다 실행  
``*/30 * * * * /root/database_backup/backup.sh`  

매주 월요일 새벽 2시에 실행  
`0 02 * * 1 /root/database_backup/backup.sh`  

매월 1일 새벽 1시에 실행  
`0 01 1 * * /root/database_backup/backup.sh`  

---  

