### IntelliJ 와 Gradle이 마법처럼 느껴진다면  
IDE의 Plugin 으로 gradle 등의 빌드+의존성관리 도구들을 별도로 설치하지 않아도 되게끔 지원해준다. Gradle을 직접 설치해서 CLI로 프로젝트를 구성해볼 것.  
npm, pip 등의 다른 의존성관리 도구와 별 다를 것이 없다.  

`gradle init` 으로 프로젝트를 생성할 수 있다.  
또는 build.gradle에 필요한 것을 명시하고 `gradle wrapper`를 통해서 gradlew를 생성할 수 있는데, 이것은 프로젝트 레벨에서 gradle버전을 독립적으로 관리하도록 하는 역할인 듯 하고 배포환경의 편의를 위함이라고 볼 수 있겠다.  
각 라이브러리들의 프로젝트레벨 격리 및 관리는 gradlew의 역할이 아니라 build.gradle(package.json, requirements.txt 등)의 역할이라는것에 주의.  

JUnit test 를 실행하려면 build.gradle 에 useJUnitPlatform을 빠뜨리지 말 것.  
settings.gradle에 rootProject.name을 추가해주는 것도 의미가 있는 것으로 보임.  
일반적으로 /src/main/java/com/example/... 의 경로를 강제하는 것인지 잘 모르겠다.  

---  
---  
---  

#### JAVA_HOME 환경변수는 왜 필요한가 ?  
다른 언어들은 별도의 환경변수 없이 컴파일러, 인터프리터의 Path 정도만 요구하는데 ?  
> JAVA_HOME 환경 변수는 JDK 설치의 루트 디렉터리를 가리키는 데 사용된다. Java 관련 도구와 애플리케이션들이 필요한 파일과 라이브러리를 찾는 데에 사용되고 있다.  

---  

#### Lazy Stream Filter
stream 문법에서, predicate 다음에 filter 가 chaining 되기 때문에 predicate 가 filter의 조건을 만족하지 않아도 항상 실행된다고 생각할 수 있다. 하지만 내부 코드에는 predicate 를 일단 저장하고, filter 를 만족하는 경우에만 실행한다.

---

#### IntegerCache
IntegerCache는 기본적으로 -128 ~ 127 까지의 Integer 객체를 캐싱한다.

---

#### Equals and HashCode  
동일성(identity) : 주소 값이 같은 것  
동등성(equality) : 내용이 같은 것  
이라고 정의할 수 있는데,  
== 연산자는 hashCode를 이용한 동일성을 검사한다.  

1. 동일한 객체에 대해서는 아무것도 하지 않아도 모든 것이 같다.  
2. 동등하지만 동일하지 않은 객체  
	- 아무것도 하지 않으면 당연히 모든 것(내용을 직접비교하지않는한)이 다르다.  
	- Equals, HashCode를 재정의하면 이 두가지 메소드를 통해 동등을 검사할수있다. 단, == 과 identityHashCode는 다르다.  
3. NestedObject (field에 Object가 있는 경우)  
	- Child도 Equals, HashCode가 같이 재정의 되어야한다.  
	
[예제코드](EqualsAndHashCodeTest.java)
	
---  

#### Generic  
타입을 일반화 한다. 목적은 Type Safety 와 유연성의 조화를 이루는 것.  
구 버전 자바 코드와의 호환성을 위해, 바이트코드레벨에서는 제거된다. Object타입을 이용해서 수동으로 타입변환을 하면서 사용할 수도 있다. 이것을 편리하게 만든것이 Generic.  
제네릭 타입 간에는 상속관계가 없다. 따라서 `<? extends A>` 라는 와일드카드를 사용한다. 순수하게 T, ? 등의 제네릭타입은 unbounded Type이라하고, 규약이 정해지면 bound type이라고 부르기도 함.  
`<? extends A>` 는 A와 A의 하위타입을 의미하고,  
`<? super A>` 는 A와 A의 상위타입을 의미한다.  
`List<? super/extends A>` 의 예시를 들어보자면,  
이 타입에 `list.add` 등의 데이터를 집어넣을때(consume)는 super를 사용하고, `for(B item : A)` 등의 데이터를 꺼낼때(produce)는 extends 를 사용한다. 상속관계를 잘 생각해보면 됨.  
PECS(Producer Extends Consumer Super)라고 부른다고 함.  

---  

#### Java Concurrent Package  
동시성 프로그래밍과 멀티스레딩을 처리하기 위한 라이브러리.  

**Executor**  
스레드 풀 생성, 생명주기관리, Task 등록 등  

**ExecutorService**   
대기열관리, 작업예약 등

**ScheduledExecutorService**  
ExecutorService와 유사하지만 주기적인 작업이 가능.

**Future**  
비동기 작업의 결과를 나타내는데 사용한다.  

**CountDownLatch**  
일부 작업이 완료될때까지 스레드 집합을 차단.  
카운트 변수를 두어 동기화를 구현하는 방식으로 보임.  

**CyclicBarrier**  
CountDownLatch와 거의 동일하고, 재사용할수있는점이 다르다고 한다.  
**Semaphore**  
말 그대로 세마포어이다.  
세마포어에 대한 면접 질문용 암기답변이 필요하다면..  
`여러 스레드 또는 프로세스의 제한된 수의 리소스에 대한 액세스를 제어하는 동기화 기본 요소로, 리소스를 사용할 수 있을 때까지 다른 스레드 또는 프로세스를 차단하면서 정의된 수의 스레드만 진행하도록 허용한다.`  

**ThreadFactory**  
스레드풀 역할. 필요에 따라 새 스레드를 생성하지않는다는 것.  

**BlockingQueue**  
생산자-소비자 패턴.  

**DelayQueue**  
만료시간(사용자 정의 지연)이 완료된 경우에만 요소를 가져올 ㅅ ㅜ 있는 요소의 무한 크기 차단 대기열.  
head가 가장 많은 지연을 가지며 마지막으로 폴링됨.  

**Phaser**  
CyclicBarrier, CountDownLatch 보다 더 유연한 솔루션.  
실행을 계속하기 전에 동적 스레드 수가 기다려야하는 재사용 가능한 장벽 역할.  

**Atomic**  
동기화 되어있는 변수 제공.  

**Synchronized**  
메소드 레벨에 붙여서 스레드 동기화가 필요한 임계 영역을 프로그래밍할 때 사용할 수 있는 키워드. 모니터 방식을 사용한다. 또는 메소드 블록 내에 synchronized(this) 또는 synchronized(lock) 등으로 블록을 만들 수도 있다. static메소드에 붙이게되면, 인스턴스레벨의 락을 사용하는지 클래스레벨 락을 사용하는지의 차이들이 있다. wait, notify는 lock요구와 반환이다.  
공유변수를 사용하는 경우 캐시 등에 주의해야 한다.  


[예제 코드](ExecutorExample.java)

---  

#### Time을 어떻게 다룰 것인가.  
LocalDateTime은 시스템 TimeZone설정에 의존한다.  
Instant는 UTC형식으로 저장하고, 2038년 문제가 해결되어 있다.  

비즈니스는 결국 Client에게 무엇을 제공하느냐가 중요하기 때문에, 필요할 경우 주고받는 데이터에 Client의 TimeZone 정보를 포함하여 처리할 수 있다.  

---  

#### Interface Specification은 Exception을 포함한다.  

---  

#### Java Collection, Map  
**Collection**  
순서나 집합과 관련한 자료구조 모음  
List : Vector(thread-safe), ArrayList, LinkedList, Stack  
Set : HashSet, SortedSet(-> TreeSet)  

**Map**  
키-값 자료구조  
HashMap, HashTable(thread-safe), SortedMap(-> TreeMap)  
ConcurrentHashMap : HashTable + 성능. 동시읽기+쓰기Lock  

---  

#### JDK 배포판에 대해서  
Eclipse Temurin, Zulu, Amazon Corretto, Microsoft, Oracle 등... 궁금하다면 대략 볼만한글 [링크](https://www.lesstif.com/java/jdk-whichjdk-com-125305293.html)  

---  

#### Annotation  

`@Target` : Annotation이 어떤 코드에 붙을 수 있는가. Type의 경우는 Class와 Enum을 의미한다.  
`@Retention` : default는 CLASS이고 바이트코드까지에 관여한다는 의미, SOURCE는 컴파일이전, RUNTIME은 말 그대로 런타임에 리플렉션등이 참조할수있다.   
`@Documented` : Javadoc에 노출된다.
`@Inherited` : 이것이 붙은 Annotation은 자식클래스도 적용되게 한다.  
`@Deprecated` : 컴파일러가 경고를 표시.  

요소들은 인터페이스에 메소드를 선언하듯 만든다. 기본타입이나 배열 등을 모두 사용할 수 있다.  
default 로 기본값을 설정할 수 있다.  

annotation은 말 그대로 Flag 역할. 아마도 무언가 복잡한 처리를하는 예제를 위해 이 단락을 찾아왔다면, annotation 자체에 대한 것이 아닌 AOP에 대한 부분을 찾아봐야 할 것임.  

---  

#### 생성자는 상속이 되지 않는다. super 키워드 사용해서 처리할 것..    

---  

#### Format String..  
`String.format("... %s", variable);`  
문자열 속에서 %s 를 읽기가 어려우니 가독성이 떨어진다 ?  

`MessageFormat.format("{0} is number", number);`  
인덱스를 일일이 넣어줘야 함.  

`method( "value is {}", variable );` 처럼 사용하고 싶다면..  
`String.format( message.replace("{}", "%s"), args);` 를 객체 생성자나 메소드 등에서 처리할 수 있음.  

---    


#### Optional.ofNullable(). orElse vs orElseGet
orElse는 메소드 등이 아닌 값 자체를 넣어야 한다. null이든 아니든 orElse 내부가 실행은 되기 때문이다.

orElseGet은 null일 경우에만 실행된다.


---






	

