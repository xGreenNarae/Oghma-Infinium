#### JUnit은 반드시 Autowired 를 주렁주렁 달아야 하는가
JUnit 의 Jupiter 가 Bean 을 직접 관리하지 않기 때문에, 생성자 주입이 안된다고 한다. @Autowired 의 반복을 해결하기 위해 TestConstructor 라는 annotation 도 존재한다고 하는데, 단순히 중복되는 코드를 상속 등을 이용해 해결해 볼 수 있음.

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


#### ParameterizedTest가 1번밖에 실행되지 않을 때
```java
@ParameterizedTest  
@CsvSource(textBlock = """  // textBlock 이 명시되지 않거나, value라고 써있지 않은지 주의.
    0,0
    1001,1.0천  
    1100,1.1천  
    10000,1.0만  
    10001,1.0만  
    11000,1.1만  
    12345,1.2만  
    """)
```




---

#### JUnit, assertAll  
assert문의 나열에서, 중간에 실패하더라도 모두 실행하여 결과들을 보여준다.  

---  

#### Mockito, MockBean vs SpyBean  
MockBean은 조작한 부분 빼고는 작동하지 않음.
SpyBean은 조작하지 않은 부분들은 정상작동.  

---  

#### WebMvcTest 에서는 사용할 Controller를 명시하자.
사용하고 있지 않은 controller의 bean도 생성하려고 하면서, bean creation error가 발생할 수 있다.

---

