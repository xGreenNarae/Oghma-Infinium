
### Owning Side, Non-Owning Side
- JPA 에서 공식적으로 사용하는 표현. 연관관계의 주인 정도가 되겠다. 조회 시 기준이 되기 때문이라고 추측.  
- ManyToOne 이 FK가 붙는 쪽이고, 여기가 Owning Side. JoinColumn 은 단순히(아마도?) 필드 이름을 명시하는 것.
- OneToMany 에서 많은 문제가 발생하는데(?), 이 쪽은 기본적으로 non-owning side. 또한 기본적으로는 별도의 join table 을 생성하는 것이고, mappedBy를 명시해줘야만 join column 방식이 된다.(**mappedBy에 들어가는 문자열은 상대쪽 클래스의 "Java 변수명"이다.**)

### OneToMany와 Cascade의 오해
문제가 여러가지인데, 서로 헷갈리기 쉽다.
Serialize, toString 부분에서 발생 하는 것이 있고(이 쪽은 적절히 exclude, ignore 해주지 않으면 발생하는 순환 참조 문제) 
이 쪽은 부모클래스(One)에 `@JsonManagedReference`, 자식클래스(Many)에 `@JsonBackReference` 붙여주자.

mappedBy를 명시하지 않고 JoinTable방식을 사용할 경우, Many쪽의 참조 필드를 명시적으로 set해주지 않아도 자동으로 설정이 된다. 하지만 N:N관계가 아니므로 JoinTable 방식을 사용할 이유가 없다.

mappedBy를 명시하여 Join Column방식으로 사용하려고 할 경우, 오해하기 쉬운 부분이 있다.
**반드시** Many쪽 참조 필드도 set해줘야 한다. Cascade가 해결해주는 것은 insert 쿼리를 추가로 보내준다는 것이지, fk까지 자동으로 설정해준다는 것이 아니다.
즉, 
```java
One.builder
	.many(
		List.of(
			Many.builder.. , // 참조 필드가 null인 상태
			Many.builder..		
		)
	)
	.build();
```
이후에 one만 save한다고 해서 fk가 말끔히 설정되는 마법은 없다.

고려해야 할 것은, 최초의 insert가 아닌
조회, 수정 시점의 객체지향적 코드관리 등 일 것이다.

OneToMany를 사용하는것이 좋은가 ? 에 대한 practical한 이야기로는.. 기존의 DB구조에서 분리되고 확장되어 나가는 코드 변경사항들에 대한 유지보수 비용을 압도적으로 줄여주긴 했다. 그 이후 관리의 어려움은 아직 경험해보지 못했음.


---

#### CasecadeRemove VS OrphanRemoval
삭제가 아닌, 연관관계의 제거(null) 만으로도 OrphanRemoval 은 삭제를 한다.  

---

#### @ManyToOne 과 @OneToOne 을 실수하는 경우는 생각보다 빈번할 것으로 예상되는데
실수하더라도 에러가 발생하는 경우는 실제로 OneToOne 으로 선언된 부분에서 Fetch 시에 2개 이상의 값이 존재하는 것을 발견하는 시점. 즉, 컴파일 타임이 아니다.  

---


