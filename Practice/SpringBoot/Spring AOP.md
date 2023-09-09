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
타겟 메소드에 특정 어노테이션이 지정된 경우  
ex) @annotation(org.springframework.transaction.annotation.Transactional) : Transactional 어노테이션이 지정된 메소드 전부  

[이외에 다양한 것들](http://jojoldu.tistory.com/71)  

@PointCut과 Advice를 분리하면, PointCut을 변수처럼 다룰 수 있다.(가독성, 재사용성)  


---  