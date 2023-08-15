### IntelliJ 와 Gradle이 마법처럼 느껴진다면  
IDE의 Plugin 으로 gradle 등의 빌드+의존성관리 도구들을 별도로 설치하지 않아도 되게끔 지원해준다. Gradle을 직접 설치해서 CLI로 프로젝트를 구성해볼 것.  
npm, pip 등의 다른 의존성관리 도구와 별 다를 것이 없다.  

`gradle init` 으로 프로젝트를 생성할 수 있다.  
또는 build.gradle에 필요한 것을 명시하고 `gradle wrapper`를 통해서 gradlew를 생성할 수 있는데, 이것은 프로젝트 레벨에서 gradle버전을 독립적으로 관리하도록 하는 역할인 듯 하고 배포환경의 편의를 위함이라고 볼 수 있겠다.  
각 라이브러리들의 프로젝트레벨 격리 및 관리는 gradlew의 역할이 아니라 build.gradle(package.json, requirements.txt 등)의 역할이라는것에 주의.  

JUnit test 를 실행하려면 build.gradle 에 useJUnitPlatform을 빠뜨리지 말 것.  
settings.gradle에 rootProject.name을 추가해주는 것도 의미가 있는 것으로 보임.  
일반적으로 /src/main/java/com/example/... 의 경로를 강제하는 것인지 잘 모르겠다.  

**IDE의 도움없이 Gradle 프로젝트를 다루지 말 것. IDE와 Gradle의 마법을 이용하고 행복한 삶을 살도록 하라.**  

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
	
---  

#### Generic  
타입을 일반화 한다. 목적은 Type Safety 와 유연성의 조화를 이루는 것.  
구 버전 자바 코드와의 호환성을 위해, 바이트코드레벨에서는 제거된다. Object타입을 이용해서 수동으로 타입변환을 하면서 사용할 수도 있다. 이것을 편리하게 만든것이 Generic.  
제네릭 타입 간에는 상속관계가 없다. 따라서 `<? extends A>` 라는 와일드카드를 사용한다.
`<? extends A>` 는 A와 A의 하위타입을 의미하고,  
`<? super A>` 는 A와 A의 상위타입을 의미한다.  
`List<? super/extends A>` 의 예시를 들어보자면,  
이 타입에 `list.add` 등의 데이터를 집어넣을때(consume)는 super를 사용하고, `for(B item : A)` 등의 데이터를 꺼낼때(produce)는 extends 를 사용한다. 상속관계를 잘 생각해보면 됨.  
PECS(Producer Extends Consumer Super)라고 부른다고 함.  

---  






	

