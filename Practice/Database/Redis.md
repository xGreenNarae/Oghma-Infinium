#### Redis 기본  
오픈소스 In-Memory Cache 솔루션으로 Redis, MemCached 를 사용할 수 있다. 특징들은 다음과 같다.    
**Redis**  
많은 자료구조 제공  
Replication 제공  
Cluster 모드 제공  
Redis의 고질적인 문제는 메모리파편화가 일어날수있는것이라고한다.  
**Memcached**  
Redis에 비해 메모리관리가 더 안정적.  
	
**Redis의 자료구조**  
Strings: Key, Value  
List: 선형리스트. 양방향입출력이가능한 파이썬의 deque느낌인듯. 배열이아님! 큐가 필요할때(RabbitMQ, Kafka 대용 등) 사용할수도 있다.     
Set: 중복없는 집합. 특정그룹을 저장할때 유용함. 친구리스트, 팔로워리스트등..  
Hash: Nested Key, Value를 의미  
Sorted Set(ZSet): 정렬이 가능한 Set. 랭킹처리등  
	
같은 해시슬롯에 들어올경우, 기본적으로 선형 리스트.  
특정 슬롯에 많이 들어가게 되면 ReBalancing을 함.  

**명령어**  
단일  
set key value  
get key  
여러개일때  
mset key value key value ..  
mget key key ...  

Redis는 싱글스레드로 돈다. 따라서 단일 mget 명령어 등은 키를 많아도 50개 정도만 하는게 좋다.(컨텍스트스위칭을위해)  

**List**  
Lpush key value  
Rpush key value  
lpop key  
rpop key  
lrange key 시작인덱스 끝인덱스  
	예: lrange key 0 -1  
brpop, blpop 은 blocking을 의미함. 데이터가 생길때까지 기다리는것. b가없으면 바로 리턴  

**Set**  
Sadd key item  
Sismember key item  // 들어있는가 1/0  
Srem key item  // 제거  
Smembers key  // 모든 item 가져오기(순서없음)  

**Sorted Set**  
기본적으로는 Skiplist 자료구조를 사용함.( find -> Log(N) )  
Double형태이므로 특정 정수값을 사용할수없다.  
Zadd key score item  // item이 이미존재하면 해당score로 변경. 여기에서는 item이 key같은 역할로 볼수있으나 value 라고 표현하는듯.  
Zrange key 시작인덱스 끝인덱스  // score오름차순으로가져옴.  
Zrevrange ... // 위와같음. 정렬순서만반대.  
ZrangeByScore key 시작스코어 끝스코어 // 스코어레인지검색  

Zrangebyscore rank (70 100 // 이런경우 간단한 SQL where절과 같은 기능이라고 보면되는데, `where score > 70 and score <= 100` 과 같은 뜻이다. (가 등호를뺀 부등호를 의미함  
Zrangebyscore rank (70 +inf // 설명 생략  

zadd(key, {"v1": 1}) // json형태로 넣을수있음.  


**Hash**  
Hset key subkey value  
Hget key subkey  
Hmset key subkey value subkey value ..  
Hmget key subkey subkey ...  
Hgetall key  
이외에도 Hvals .. 등 ..  


**Redis Transaction**  
Redis는 싱글스레드이기때문에 명령들이 atomic을 보장한다는것이 정확한 의미.  
Multi/Exec  

**Redis Pipeline**  
실제로 Redis에 존재하는 개념이 아닌, Library에서 제공하는것. Async Redis Client라면 따로 제공할 필요가 없다고함.    
Redis로 명령을보내고 나서 응답을 받기까지 기다리는 Time Gap을 아끼기 위해, 일련의 명령어들을 응답을기다리지않고 보내는 것. 100만개를 set하는 테스트에서 10배이상의 차이가남. 쉽게 생각해서 batchInsert 의 느낌이라고 보임.  

여기서 주의할것. Pipelining은 Atomic한것이 아니라는것이 Transaction과의 차이점. 중간에 다른 클라이언트의 명령 등을 허용함.  
예를들어 Python Redis Client의 경우 기본적으로 pipeline 구문이 MULTI/EXEC을 사용하게 되어있는 등.. 사용 시 라이브러리를 잘 확인하라고 함.  

---  

#### Redis 에 대해서  
어디에쓰는가?  
Cache, Job Queue, Ranking 처리 등 ...  
**Rate Limit**에 많이 사용.  
View Count 등에도 사용할수 있음.  


Cache 솔루션으로 많이 사용할 수 있는데, 여기서 중요한 것은 결국 **파레토의 법칙**. 캐시가 얼마나 효율적일지에 대해 상황을 잘 파악해야할 것.  

**API Cache**  
해당 API가 writable하거나.. 자주변하는값인 경우 등에 캐시를 사용하기는 어려울 것. 보통 사용되는 Look-Aside 캐시의 경우, 데이터변경이 발생했을때 해당 캐시를 지우는 방식을 사용한다.    
**Key 설계**  
- Key는 유일해야함.  
- Endpoint 만으로 유일서이 보장되는지
- 특정 유저나 특정 기간의 정보인가 등..  
- Prefix를 잘 이용하면 좋음.  
예를들어 prefix+endpoint+userId라면, `api:maps:1234` 이런식을 생각해볼수있음  

**AWS ElastiCache Redis**  
Redis Managed Service  
FailOver등도 쉽게 지원  

**Redis Metrics**  
`info all` 명령으로 Redis 자체에서 제공해주는 Metrics를 확인할 수 있다.  

- memory  
used_memory_rss : 가장먼저확인할것. 사용중인 실제 물리메모리의 양. 사용량이 많으면 Swap이 일어나서 성능이 떨어짐.  
used_memory : 현재 Redis가 계산하고 있는 사용메모리양.  
mem_fragmentation_ratio : 1보다 높으면 fragmentation이 높다. 1보다 적으면 swap이 발생하고있다고 보면된다.  

- stats  
instantaneous_ops_per_sec : 초당실행명령수.  
...  

- client  
- Replication  

commandstats 파트의 cmdstat_keys:calls를 잘 확인해야 한다고 한다.(모니터링 툴에서 잡아두고 쓰는 경우 등)  


---  

#### Key Naming Convention  
관례는 콜론이다. 슬래쉬를 사용하는것을 쉽게 떠올릴수있으나, 비판적인 의견들이 있고 이유를 아직 이해하지는 못했음.  

일반적으로 이렇게.  
`user:1000:password`  

네임스페이스 구분 기호로 콜론을 사용하고, 키의 ID부분에는 해시를 사용할 수 있다.  
`location:building#23`  

`object-type:id:field` 예제도 있다.  

단어구분에 `.` 을 사용하는 사람도 있다.  
`object.type:id:field`  

---  


 

