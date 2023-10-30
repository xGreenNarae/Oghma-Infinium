
#### 객체 상태가 변경되면, Transaction 종료 시 dirty-checking이 자동으로 수행되어 변경사항이 DB에 반영된다.
JPA의 핵심. update쿼리를 수동으로 사용할 필요 없다.

---

### 영속성 컨텍스트와 DB 조회 데이터가 일치하지 않으면, DB의 것을 버린다.
update query 이후, 변경된 데이터를 조회하려면 컨텍스트를 비워주도록 한다.


---

#### JPA 에서 2차캐시라는 이름으로 부르는 것
Shared Cache 로, 트랜잭션 간에 공유되며 Application 의 생명주기와 함께하는 캐시. Annotation 을 이용해서 적용할 수 있다. → Spring Cache 로도 충분할 것 같은 느낌 ?

