#### Spring AOP  
Target  
Aspect  
Advice  
PointCut  
JoinPoint  
Proxy  
Introduction  
Weaving  

PointCut   
**execution()**  
접근제한자, 리턴타입, 인자타입, 클래스/인터페이스, 메소드명, 파라미터타입, 예외타입 등을 전부 조합가능한 가장 세심한 지정자  
풀패키지에 메소드명까지 직접 지정할 수도 있으며, 아래와 같이 특정 타입내의 모든 메소드를 지정할 수도 있다.  
ex) execution(* com.blogcode.service.AccountService.*(..) : AccountService 인터페이스의 모든 메소드  

**@annotation**  
타겟 **메소드**에 특정 어노테이션이 지정된 경우(메소드레벨에 사용하는것임이 중요!)  
ex) @annotation(org.springframework.transaction.annotation.Transactional) : Transactional 어노테이션이 지정된 메소드 전부  

**@target**, **@within**
**클래스** 레벨 포착. target은 자신뿐 아니라 부모클래스에도 적용한다는 점이 차이다.

이외에 **메소드 파라미터** 레벨을 포착하려면 **execution**을 사용해야한다.

[이외에 다양한 것들](http://jojoldu.tistory.com/71)  

@PointCut과 Advice를 분리하면, PointCut을 변수처럼 다룰 수 있다.(가독성, 재사용성)  


---  

#### Parameter AOP

**HandlerMethodArgumentResolver** 를 이용하여 더 간단하게 해결할 수 있는 문제가 아닌지 생각해볼 것.

다음과 같은 것을 하고 싶은 상황이다.
```
@GetMapping("/url")  
public ... someMethod(@CustomAnnotation Variable variable) {  
    ...  
    // 가공된 variable을 받아낸다. 예를들면, Request Header의 Authorization으로 부터 Token을 꺼내서 검증하고, Token으로 부터 받아온 사용자 정보(User 등)다.
  
    return ...
}
```

핵심은 `pointCut` 에서 사용하는 `@annotation`은 `method level annotation`에 적용할 수 있는 것이고.. `parameter level annotation` 을 처리하기 위해서는 다음과 같이 사용해야 한다.
``` 
@Aspect  
@Component   
public class CustomAspect {  
 
    @Around("execution(* *(.., @CustomAnnotation (*), ..))")  
    public Object someMethod(final ProceedingJoinPoint joinPoint) throws Throwable {  
        try{  
            ... 
  
            return joinPoint.proceed( joinPoint.getArgs()... );  
        } catch (final Exception e) {  
            ...  
        }    
	}

}

```

---

#### execution pointcut

[공식문서](https://docs.spring.io/spring-framework/reference/core/aop/ataspectj/pointcuts.html#aop-pointcuts-examples)

```
execution(접근제어자? 반환타입 선언타입?메서드이름(파리미터) 예외?)

execution(modifiers-pattern?
			ret-type-pattern
			declaring-type-pattern?name-pattern(param-pattern)
			throws-pattern?)

```

? 표시는 생략 가능을 의미함.
이름 패턴을 적는 것이므로.. "특정문자를 포함한 메소드 이름" 이런것도 가능하다.

---

#### Class Level, Method Level annotation을 모두 활용하는 법

`@Transactional` 과 유사하게 구현하려고 하는 것인데,
class level 또는 method level에 annotation이 붙을 수 있고, 우선순위가 method level이 더 높은 것이다.

```
// PointCut은 이런 식. Class, Method Level 에서 둘 다 포착하는 것이다.
@Around("@within(MyAnnotation) || @annotation(MyAnnotation)")


// Class Level Annotation Value
joinPoint.getSignature()
	.getDeclaringType() // 여기까지가 Class 를 가져오는 것
	.getDeclaredAnnotation(MyAnnotation.class)


// Method Level Annotation Value
( (MethodSignature) joinPoint.getSignature() )  
    .getMethod()  
    .getAnnotation(MyAnnotation.class)
    .value();
```

---  

