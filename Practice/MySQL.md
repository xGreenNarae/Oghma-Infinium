### Replication  
#### GTID 를 이용한 복제 서버 구축  

2개의 mysql 서버 준비.

mysql 설정파일을 어느 경로부터 확인하는지 주의할 것.  
일단 `/etc/my.cnf` 파일이 우선인 것으로 확인하고 이 파일에 설정했다. 

SOURCE   
```
[mysqld]
gtid_mode=ON
enforce_gtid_consistency=ON
server_id=1111

log_bin=mysql-bin
```



REPLICA  
```
[mysqld]
gtid_mode=ON
enforce_gtid_consistency=ON
server_id=2222

relay_log=mysql-relay-bin
relay_log_purge=ON
super_read_only # 책에는 read_only 를 설정하라고 나와있으나 super_read_only 가 더 안전하다.
log_slave_updates
```

대략 이런 식이다.  
log file 경로를 설정할 때, 없는 디렉토리 넣으면 안된다. 아마 OS의 기본적인 내용일 것 같은데.. 잘 몰라서 삽질했음.  

설정파일을 바꿨으면 `RESTART` 해줄 필요가 있다. 
제대로 반영되었는지, `SHOW VARIABLES LIKE 'gtid_mode';` 등으로 확인해줘도 좋겠음.  

이제 기존 데이터를 복사한다.  
`mysqldump -uroot -p --single-transaction --master-data=2 --set-gtid-purged=ON --opt --routines --triggers --hex-blob --all-databases > source-data.sql`  
이런 식이다. master 부분 명령어가 deprecated 되었을 수도.  

생성된 파일은 replica 서버로 옮긴다.  

레플리카에서 데이터 복구할 때,  
OS 상에서라면 `mysql -u root < source_data.sql -p` 이후 패스워드 입력으로.  
SOURCE 명령어를 사용하는건 mysql prompt 에서.  

데이터 복사가 완료되면 gtid_executed, purged 시스템 변수가 자동으로 설정된다. 이 부분이 어떤 의미인지 아직 모르겠음. 

복제를 시작하려면 다음과 같은 명령어.   
```
CHANGE REPLICATION SOURCE TO
	SOURCE_HOST='172.17.0.1',
	SOURCE_PORT=3307,
	SOURCE_USER='repl_user',
	SOURCE_PASSWORD='PASSWORD',
	SOURCE_AUTO_POSITION=1,
	GET_SOURCE_PUBLIC_KEY=1;
```
도커를 사용한 경우 게이트웨이 주소를 저렇게 쓸 수 있다.  
이 부분에서는 password가 32byte 제한을 거는 것 같던데..  

`SHOW REPLICA STATUS;` 로 상태를 확인해보자. WAITING 중이라면 OKAY.  
만약 자동으로 시작이 안된다거나.. 하면 `START REPLICA` 등의 명령어가 있다. 반대로 `STOP REPLICA` 도 있음.  

---  
#### 복제에 문제가 발생하여 멈췄을 경우. 문제지점의 트랜잭션을 건너뛰기  
`SHOW REPLICA STATUS` 를 통해 retrieved_gtid_set, executed_gtid_set 을 확인할 수 있다.  
예를들어 retrieved_gtid_set 이 `09c5fc8b-3787-11ee-9817-0242ac110004:7-22` 이고,  
executed_gtid_set은 `09c5fc8b-3787-11ee-9817-0242ac110004:1-17`,
`4fde3a8b-3787-11ee-9937-0242ac110004:1-2` 라고 되어있다 치자.  

이것을 잘 복사해두고..  
`STOP REPLICA;` 로 복제를 중단한다.  
`SET gtid_next = '09c5fc8b-3787-11ee-9817-0242ac110004:18'` 이 되어야 한다. `4fde..` 은 필요없는거고, 17번까지 수행되었으니 그 다음이 18번이 되어야한다는 뜻. gtid 부분에 따옴표로 감싸지는것 잘 볼것.  
빈 트랜잭션을 생성하라. `BEGIN; COMMIT;`  
gtid_next 변수값이 자동으로 초기화되도록 설정 `SET gtid_next='AUTOMATIC';`
`START REPLICA;` 로 복제를 재시작 하자.  

---  
---  
---  




