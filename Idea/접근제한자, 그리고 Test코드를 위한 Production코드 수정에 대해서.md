#### 문제

예를 들어, 일반적인 "게시글(Post)" 이라는 데이터에 대해 생각해보자.

id(pk), content(글 내용), viewCount(조회수)를 정의하였다.

```java
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {  
  
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Integer id;  
  
    @NotNull  
    private String content;  
  
    @NotNull  
    private int viewCount;  
              
    @Builder  
    public Post(final String content) {  
        this.content = content;  
        this.viewCount = 0;          
    }
}
```

문제는 현재, 게시글이 생성될 때는 viewCount가 항상 0으로 초기화 될 것을 의도하고 있다.
따라서 생성자 레벨에서 아예 viewCount에 대한 조작을 막아버린다. 
의도치 않은 실수 발생을 막을 수 있을 것 이라는 생각에 만족스럽다.

그리고 서로 다른 viewCount를 가진 Post들을 내림차순으로 조회하는 요구사항이 정의 되었다.

이제 해당 쿼리를 만들었다고 하고, 
그걸 테스트하고자 하는 상황을 가정해보자.
```java
@Test
void queryTest() {
	Post.builder().viewCount(10).build();
}
```
당연히 안된다.

이것을 어떻게 해결할 것 인가가 이 글의 주제다.

---

#### 고민

1.  `AllArgsConstructor + Builder`를 열어버린다.
가장 간단한 해결책이다. 
```java
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {  
  
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Integer id;  
  
    @NotNull  
    private String content;  
  
    @NotNull  
    private int viewCount; // 0으로 초기화 해주세요.
              
}
```
주석으로, **viewCount는 항상 0으로 초기화 해주세요.** 정도를 적어둔다.

장점은 구현이 가장 빠르고 쉽고 간단하다는 것이다.
단점은 **약속 기반** 해결이다. 이제 현재와 미래의 이 코드에 대한 모든 작업자들의 머리 속에 이 한 가지 약속을 집어 넣어야 한다. 
더 큰 단점은, **단일 관리 포인트가 사라진다**는 것이다. viewCount가 1로 초기화 되어야 하는 상황이 생기는 경우 등.

참고로 [가변 이니셜라이저를 이용한 초기화는 Builder에 적용되지 않는다.](obsidian://open?vault=Oghma-Infinium&file=Practice%2FJava%2FBuilder%EB%A5%BC%20%EC%82%AC%EC%9A%A9%ED%95%A0%20%EB%95%8C%2C%20%ED%81%B4%EB%9E%98%EC%8A%A4%20%ED%95%84%EB%93%9C%EC%9D%98%20%EA%B8%B0%EB%B3%B8%20%EA%B0%92%20%EC%84%A4%EC%A0%95)



2. `AllArgsConstructor + Builder + Static Factory Method`
```java
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {  
  
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Integer id;  
  
    @NotNull  
    private String content;  
  
    @NotNull  
    private int viewCount;
	
	public static Post startsWithZeroViewCount(final String content) {  
	    return Post.builder()
		    .content(content)
		    .viewCount(0)
		    .build();
	}

	public static Post withViewCount(final String content,
									 final int viewCount) {  
	    return Post.builder()
		    .content(content)
		    .viewCount(viewCount)
		    .build();
	}    
}
```
관리 포인트가 분산되는 문제를 조금 개선했는지도 모르겠다.
`withViewCount`라는 부분을 테스트 코드에 가져다 쓰면 되겠다. 물론 "테스트 코드에서만 쓰자" 라고 **약속**을 해야 한다. 또는 약속 하지 않고서도 코드를 읽는 사람들이 이해하고 잘 쓰겠지 라고 믿어도 좋다.
지저분한 메소드 이름이 불만이다. 이것을 고민하기 위해 추가로 1시간을 투자할 수도 있겠다.

`public` 접근제한자를 `package-private`등 으로 수준을 낮춰서 제한해보는 방법도 있겠다.

---

#### Test Code 때문에 Production Code를 수정하는게 불만이다.

3. 데이터를 직접 저장하고 쿼리 해야하는 상황이 아닌, 값을 읽어오기만 하면 되는 상황이라면 `mocking` 을 적절히 사용할 수도 있다.

4. Reflection 을 사용한다.
`private`으로 선언된 `viewCount`의 접근제한자를 뚫고, 값을 집어 넣겠다는 것이다.
테스트 코드 때문에, Production Code를 수정하지 않아도 되고, 관리 포인트가 분산되지도 않고 실수가 발생할 수 있는 상황에 대해 특정한 코드 사용 규칙 약속을 추가로 만들지 않아도 된다.

단점들이 다 해결되어서 충분히 좋은 해결책이다.
Reflection 코드에 익숙하지 않다면 조금 구현이 번거로울 수 있겠다.

근본적인 문제 해결은 아니다.
Reflection을 사용하는 것은 컴파일러의 접근제한자 지원을 제거하는 것이다. 누구나 Production 코드에서 Reflection을 사용해서 `viewCount`를 0이 아닌 이상한 값으로 초기화 할 수 있다. 다만 그렇게 하지 않을 것이라는 믿음이 전제되어 있을 뿐..
이 믿음이 결국 앞에서 이야기했던 **약속** 이다.

작업자, 팀 차원에서 새로운 약속을 만들어서 문제를 해결하고자 했던 위의 해결책들과 근본은 같다는 것인데, 언어 차원에서 약속을 해둘 수 도 있다.
예를들어 `_viewCount` 처럼 언더바로 시작하는 변수는 외부에서 접근하지 말자는 것이다. 
약속이 잘 만들어져 있다면 힘들게 Reflection등의 구현을 할 필요도 없고, 심지어 `private`등의 컴파일러 레벨 접근제한 검사도 둘 필요가 없다.


5. `sql`로 직접 데이터베이스를 조작한다.
테스트 코드 실행에 앞서, DML을 실행해주면 된다.
접근제한자 등의 코드 레벨 제약에서 벗어나 테스트 하고자 하는 대상(DB)을 직접 조작하는 것이고,
코드 레벨에서 발생할 수 있는 부작용들을 모두 제거한 좋은 해결책으로 보인다.
