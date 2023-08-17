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

#### 양방향 복제 구성 - 듀얼소스  
Active-Passive : 일단 한 곳에만 쓰기를 하다가 문제가 생기면 바로 예비 DB에 쓰기를 할 수있는 형태.  
Active-Active : 보통? 지리적으로 떨어진 위치에서 유입되는 쓰기 요청을 원활하게 처리하기 위해 사용한다는데..?  

유의할 점(정합성유지)은 다음과 같다.  
동일한 데이터를 각 서버에서 변경 : 일단 나중에 발생한 트랜잭션의 값으로 덮어씌워진다. 분산환경에서 잠금을 어떻게 처리할지에 대해 생각해보면 되겠다.  
테이블에서 Auto-Increment key 사용 : 책에는 일단 사용을 지양하는 방향을 생각해보라고 권장되어있고, 사용하고자 할 경우에는 auto_increment_offset 과 auto_increment_increment 시스템 변수를 사용하라고 한다. 이것은 일종의 숫자 증가 영역을 샤딩한다고 생각하면 되겠다.(1,3,5,7.. / 2,4,6,8..)  

**여기서 중요한 것은, 우리가 왜 이 단락을 지금 읽고 있는지에 대해 생각해보자.**  
쓰기 트래픽을 분산시켜 처리량 향상에 도움이 될 것을 기대할 수 있으나, 사실 복제과정에서 모든 트래픽을 각 서버들이 결국 처리해야하므로 **효과가 크지않다** 라고 책에 나와있다. 오히려 분산환경에서 새롭게 발생하는 문제점들이 많은 편이라고 한다. 책에서는 **쓰기 성능의 확장이 필요할 경우 샤딩을 권장**한다.  

**Auto FailOver Open Source**  
MMM(Multi - Master Replication Manager), MHA(Master High Availability) 등이 있다.  

MMM의 경우, 마스터가 변경되었는데 Slave가 이미 기존 Master에게 받은 마지막 데이터를 이미 가지고있어서 **중복 복제 시도** 가 발생하여 PK오류가 발생할 수 있음.  
MHA는 Source에 장애발생 시, 믿을 수 없다고 판단하고 복제를 끊는다. 그리고 **가장 최신의 데이터**를 가지고 있는 DB를 마스터로 승격시킨다. 장애복구 완료시 복제를 재구성해줘야 한다.  

---  
---  
---  

#### 임시 쿼리 로그를 보고 싶을 때  
`SET GLOBAL log_output = 'table';` # 로그를 파일이 아닌 테이블에 기록.  
`SET GLOBAL general_log = 1;`  
`SELECT * FROM mysql.general_log ;`  

---  

#### 유저 생성 및 권한 부여  
`CREATE USER 'userName'@'%' IDENTIFIED BY 'PASSWORD';`  
`GRANT ALL PRIVILEGES ON *.* TO 'userName'@'%';` # 이 경우 모든 권한을 준다.  

---  




