### 객체 상태가 변경되면, Transaction 종료 시 dirty-checking이 자동으로 수행되어 변경사항이 DB에 반영된다.
백기선님이 이거 모르면 JPA 쓰지 말고 JDBC 쓰라고 함.
그도 그럴 것이 이게 ORM 의 핵심인 것 같은데..

---

### 영속성 컨텍스트와 DB 조회 데이터가 일치하지 않으면, DB의 것을 버린다.
update query 이후, 변경된 데이터를 조회하려면 컨텍스트를 비워주도록 한다.

### Owning Side, Non-Owning Side
- JPA 에서 공식적으로 사용하는 표현. 연관관계의 주인 정도가 되겠다. 조회 시 기준이 되기 때문이라고 추측.  
- ManyToOne 이 FK가 붙는 쪽이고, 여기가 Owning Side. JoinColumn 은 단순히(아마도?) 필드 이름을 명시하는 것.
- OneToMany 에서 많은 문제가 발생하는데(?), 이 쪽은 기본적으로 non-owning side. 또한 기본적으로는 별도의 join table 을 생성하는 것이고, mappedBy 를 명시해줘야만 join column 이 된다.

---
---
---

#### Bean내부에서 같은클래스의 method를 호출할때는 Transactional이 동작하지 않는다
실행 컨텍스트가 annotation 을 읽는 시점을 생각해보자.  

---

#### CasecadeRemove-VS-OrphanRemoval
삭제가 아닌, 연관관계의 제거(null) 만으로도 OrphanRemoval 은 삭제를 한다.  

---

#### CheckedException은 RollBack 되지 않는다

---

#### Index, DB Constraints 등은 Entity에 정의하지 않는다.
Index 는 상황에 맞게 쿼리를 최적화 하기 위한 것, DB 에 책임이 있다.  
Entity 레벨에서 관리해야할 제약조건과 DB 레벨에서 관리해야할 제약조건은 쓰임이 다를 수 있다.

--- 

#### @ManyToOne 과 @OneToOne 을 실수하는 경우는 생각보다 빈번할 것으로 예상되는데
실수하더라도 에러가 발생하는 경우는 실제로 OneToOne 으로 선언된 부분에서 Fetch 시에 2개 이상의 값이 존재하는 것을 발견하는 시점. 즉, 컴파일 타임이 아니다.  

---

#### JPA 에서 2차캐시라는 이름으로 부르는 것
Shared Cache 로, 트랜잭션 간에 공유되며 Application 의 생명주기와 함께하는 캐시. Annotation 을 이용해서 적용할 수 있다. → Spring Cache 로도 충분할 것 같은 느낌 ?

---

#### Transactional
readonly 는 실제 쿼리호출을 방해하지 않는다. dirty-checking 시에 write를 막는다.