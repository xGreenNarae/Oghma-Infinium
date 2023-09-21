
[손권남 님의 좋은 자료](https://kwonnam.pe.kr/wiki/gradle/multiproject)

#### 개요

- 공통 코드를 재사용하기 위함.  
예를들어, 동일한 Entity와 Repository들을 참조하여 하나는 Api, 또 다른 하나는 Batch 작업을 하는 프로젝트 등을 생각해볼 수 있다.  
구현은 gradle 설정에 대해 조금 신경쓰면 된다.  
[예제 코드](../examples/multi-module-example/build.gradle)

- 수평으로 프로젝트를 떼어낸다.
모듈 간 통신하는 MSA 구조를 이야기하는게 아니라, 단순히 빌드와 배포를 빠르게 처리한다는 장점

- 모듈 간 의존성을 빌드스크립트로 좀 더 쉽게 파악할 수 있는것도...

---

#### 빌드 스크립트에서 주의할 점

**allprojects vs subprojects

[일단 Gradle에서 공식적으로 권장하지 않는다고 한다.](https://docs.gradle.org/current/userguide/sharing_build_logic_between_subprojects.html#sec:convention_plugins_vs_cross_configuration)

allprojects 는 root 까지 적용
subprojects 는 모든 서브프로젝트 적용


buildscript의 dependencies 에 이것 넣으면 하위 프로젝트에서 gradle wrapper를 찾아다니면서 오류를 뿜는 듯하다..
`classpath "io.spring.gradle:dependency-management-plugin:1.0.15.RELEASE"



**plugins "java-library"
```
plugins { id "java" } // 이게 없으면 안됨.

repositories { // 라이브러리를 불러올 원격 저장소를 Maven Repo로 설정
    mavenCentral()
}
```

maven repo가 아닌 프로젝트 내부의 모듈을 불러와서 의존성으로 사용하려면,
```
plugins { id "java-library" } 
```
가 필요하다고 한다.(정확하지 않음.)


**dependency 에서, api vs implementation

의존성 명시할때 api 키워드는  "컴파일" 타임 의존성을 나타낸다고 한다.

**implementation**

	1. 타입이 메소드 바디 안에서만 쓰이는 경우
	2. 타입이 private 맴버(변수/메소드 등등)에서만 쓰이는 경우
	3. 타입이 인터널 클래스에서만 쓰이는 경우

**api

	1. 타입이 인터페이스나 슈퍼 클래스에서 쓰이는 경우
	2. 타입이 public/protected/package/private 메소드의 파라미터(메소드의 인자, 반환 타입 및 타입 파라미터)에서 쓰일 때
	3. 타입이 public 필드에서 쓰일 때
	4. public 어노테이션 타입일 때

---

모듈 패키지와 파일들은 당연히, 수동으로 추가하는 것이 아니라, IDE에서 "모듈 생성" 을 사용할 수 있다!

---

단위 테스트 시 모듈 간 의존성이 발생할 때
```
plugins {
    id 'java-test-fixtures'
}
```
에 대해 알아볼  것..


---



