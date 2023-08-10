### 인터페이스 타입 빈 주입을 시도하면 자동으로 구현체를 찾는다. 2개 이상이면 기본적으로는 에러다.

---
---
---

#### DateTime 이 배열로 나오는 문제
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul") 붙여준다.  

---

#### Controller Response Content-type
ObjectMapper 등을 이용하든지로.. String type 을 return 하면 기본적으로 Content-type 이 text/html 이다.  

---

#### @Valid 는 순서가 보장되지 않는다.
Validation 라이브러리의 @Validated 기준, 선언된 제약조건들이 ConcurrentHashMap 이라는 자료구조에 저장 - 접근 되기 때문에 매 요청마다 검사 순서가 무작위로 발생하는 것으로 보인다. 확신이 없기 때문에, Question 에 남겨둔다.

---

#### RequestBody 에 들어가는 Dto 의 Field 가 1개일때는 zero-parameter 생성자가 없을 경우 오류가 발생한다.
- Jackson 에 연관된 사항으로, 좋은 이야기가 이미 있다.(제대로 읽어보지는 않음) 필드가 하나인 경우 굳이 객체를 사용할 이유가 없기 때문이 아닐까라고 생각해봤다. 이것 하나를 해결하기 위해서 여러가지 다른 문제를 일으킬 여지들이 많다는 뉘앙스 인 듯..
  - https://github.com/mock-rc4/coupangEatsB-test-server-ashley/issues/15
- 내가 선택한 해결책은 그냥 zero-parameter 를 추가해서 사용하는것. 또는 Dto 의 경우 Setter 를 사용해도 괜찮을 것 같다.  

---

#### RestControllerAdvice 가 여러 개일 경우, 순서가 보장되지 않는다.
@Order 로 관리해줄 수 있다.

---

#### JUnit은 반드시 Autowired 를 주렁주렁 달아야 하는가
JUnit 의 Jupiter 가 Bean 을 직접 관리하지 않기 때문에, 생성자 주입이 안된다고 한다. @Autowired 의 반복을 해결하기 위해 TestConstructor 라는 annotation 도 존재한다고 하는데, 단순히 중복되는 코드를 상속 등을 이용해 해결해 볼 수 있음.

---

#### build.gradle, Dependency 는 소스코드에서 가져다 쓸 jar 파일들을 의미하고, plugin 은 빌드 과정에서 사용될 jar 파일들.

---

#### Open Session In View
- spring.jpa.open-in-view 를 application.yml 에서 false 로 설정할수있음. 기본값은 true
- false 시에 DB 세션의 수명을 transaction 과 동기화. Controller 단에서 Read 를 막는것을 의미한다.
- 트래픽이 적은 admin page 같은 경우에 쓸만할 듯.
    
---

#### @Valid 는 List type 에서는 동작하지 않는다.
Class level @Validated 로 해결할 수 있다.

---

#### Controller URL Mapping 시 "" 와 "/" 는 다르다.
둘 다 존재한다면 "/" 가 우선 매핑된다. 구체적인 동작의 원리는 이해 못했음.

---

#### LomBok은 당연하지만, 컴파일타임에 코드검색을 할 수 없다.

---

#### JUnit, ParameterizedTest + CsvSource에 NullValue 넣는 방법
```
@ParameterizedTest
@CsvSource(value = {"0,Name1", "2,Name2", "3,null"}, nullValues = "null")
void test(final int index, final String name) {
  System.out.println("index = " + index);
  System.out.println("name = " + name);

  assertThat(index).isLessThan(4);
  assertThat(name).isNotNull(); // Fail
}
```

---

#### JUnit, assertAll  
assert문의 나열에서, 중간에 실패하더라도 모두 실행하여 결과들을 보여준다.  

---  

#### Mockito, MockBean vs SpyBean  
MockBean은 조작한 부분 빼고는 작동하지 않음.
SpyBean은 조작하지 않은 부분들은 정상작동.