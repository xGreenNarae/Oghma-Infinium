#### Replication  
복제에는 State기반 복제, Row(Command)기반 복제로 나눠볼 수 있는데 상태를 전달하는것과 명령을 전달하는 것에 차이가 있다.  
예를들어 전달받은 명령이 Now() 일 경우, 서버 시스템 시간의 차이가 발생할 수 있는것.  

---  
---  
---  

### MySQL  

#### RDBMS 에서 - [Silvia Botros, Jeremy Tinley]
- 1개 이상의 값을 가질 수 있는 필드에 SET Type 을 사용하는 것이 어떤가하면, 일반적으로는 별도의 Relation 을 운용하는 것이 좋다고 함.
- Query Tuning 보다는 Optimization 이라는 단어가 더 적절하다.
- 복잡한 하나의 쿼리 vs 여러 개의 간단한 쿼리
  - 이론적으로나 역사적으로나 단일 쿼리가 좋다고 한다. (네트워크 I/O, 구문 분석 및 최적화 비용)
  - 네트워크 지연이 계속 줄어들고 있고, MySQL의 연결관리, 간단한 쿼리 처리 등에 최적화된 기술 덕에 크게 의미있는 이야기는 아니라고 한다.
  - 작업 비용에 우선하여 생각하면서, 지속적으로 모니터링하면서 결정할 것. 생각보다 비효율적인 쿼리를 많이 사용하며 운영되고 있는 어플리케이션들이 있다고 한다.

#### PK는 무엇으로 해야 하는가
- `Column 의 크기가 크더라도 비즈니스적으로 해당 레코드를 대표할 수 있다면 그 칼럼을 PK 로 설정하는 것이 좋다` 라는 표현이 Real MySQL 에 있는데, 너무 낙관적이고 안일한 표현으로 느껴졌다. 이메일이 Unique 하고 Not Null 이라고해서, PK 로 잡아두었다가, 계정삭제와 재가입 또는 이메일변경등의 비즈니스 스펙이 변경되면 어떻게 대응할 것인가. 최대한 변하지 않을 것으로 설정하자. 예상이 안되면 그냥 Auto Increment 를 사용하자.

#### Unique Index
라는 표현이 있는데, MySQL은 공식문서에 Unique Index Constraint 라는 표현을 사용한다. CREATE UNIQUE INDEX .. 구문이나 ADD CONSTRAINT .. UNIQUE KEY .. 구문이나 차이가 없는 듯. Unique Index, Unique Constraint 를 구분하고, 동작의 차이가 있는 DB도 있는 것 같음.
  
#### Hash Index
- 기본적으로 Key-Value 시스템에서 많이 사용.
- InnoDB 에서 지원하지 않는다.(GUI 에 클릭할 수 있게 되어있는 경우로 가능하다고 착각할 수 있음)

#### InnoDB 가 무슨 뜻인가.
- InnoBase 라는 회사에서 만들어진 이름으로 추정. Innovated DB 정도의 이름일까 라는 추측 정도.

---  
---  
---  

