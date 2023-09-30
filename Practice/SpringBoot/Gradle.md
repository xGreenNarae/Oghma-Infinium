

#### build 가 오래걸릴 때..


**gradle.properties

root directory에 gradle.properties 를 추가하여 설정을 만질 수 있는데..

```
org.gradle.daemon=true # 기본값이 true 라고한다.
org.gradle.parallel=true # multi module 등의 구조에서 병렬 빌드
org.gradle.caching=true
```

**분석 해보고 싶다면

`gradlew --profile`
시간 등을 측정해서 report.html 을 만들어준다.

이외에
`gradlew build --warning-mode all`

`gradlew build --scan` 의 경우, Gradle Enterprise 라는 사이트로 연결되서 분석해주는듯..

**test를 병렬로 실행하는 설정

공식적으로는 다음과 같이 사용가능한 CPU의 절반을 쓰는 것을 추천한다고 함.
```
tasks.withType(Test) {
    maxParallelForks = Runtime.runtime.availableProcessors().intdiv(2) ?: 1
}
```

**test report 생성 설정 끄기
```
tasks.withType(Test) {
    reports.html.enabled = false
    reports.junitXml.enabled = false
}
```

생성하지 않으면 성능 향상에 도움이 된다고 하는데..


**parallel gradle build 설정이 켜져 있다면, java compile 작업을 별도의 프로세스로 실행하게 하는 설정.

빌드타임 중에 재사용 되므로 fork 오버헤드와 daemon의 GC를 줄여준다고 함..
```
tasks.withType(JavaCompile) {
    options.fork = true
}
```


[Gradle 공식 빌드 최적화](https://docs.gradle.org/current/userguide/performance.html)


유의미한 효과가 나는것을 측정해보지 못했음.

---
---
---

#### Groovy DSL

**source compatibility vs target compatibility

source는 버전 고정
target은 최소 버전을 명시하는 것이라고 한다.

---

#### build vs bootJar

`bootJar` 는 단순히 jar파일을 만드는 작업
`build` 는 이외에도 test 등 다른 작업들을 포함한다. 시간이 더 걸린다.