#### Spring Cache  
Spring Cache Abstraction 이라는 기술을 통해 지원된다.  
인터페이스이고, 캐시 구현체를 선택하라는 뜻.  

application에 `@EnableCaching` 어노테이션 설정이 추가되어야 한다.  
기본구현체는 ConcurrentMapCacheManager 라고 하고 프로덕션에 사용하기에 성능이 좋은것이 아니라고 함.  

이외 기본적인 어노테이션들. 메소드단위로 걸어주면된다.  
`@Cacheable` 메소드에 캐시 트리거 설정  
`@CachePut` 이 메소드는 항상실행한다. 캐시를 갱신하기위해 사용한다.    
`@CacheEvict` 캐시 되어있는 데이터 지우기  
`@CacheConfig` 캐시 관련설정(클래스단위)  
`@Caching` 예를들어 CacheEvict 등을 "여러개적용"해야하는 경우 등  

속성  
cacheNames  캐시이름
value : cacheNames의 alias  
key 키(기본값은 파라미터). 동일한 cacheNames값을 가지고있으나 구분이 필요한경우.    
condition : 참일경우 캐시적용    
unless : condition과 반대. 참일경우에 캐싱적용 X  
sync : 캐시구현체가 thread-safe하지않은경우, 동기화를 위함.  

`@Transactional` 등과 마찬가지로 Spring AOP를 이용하므로, public method에다가만 적용할 것.  


**EHCache**  
이름의 의미는 회문이다. Adam Murdoch가 작성한 것으로 추정.  
패키지 이름이 두개가 있는데, `org.ehcache..`과 `net.sf.ehcache..` 이다. ehcache3의 경우 전자를 사용해야하는것으로 보임. 2와 3의 차이에 대해 알게되면 이곳의 내용을 갱신하자.  
EHCache 3  
Exception이 발생하는 메소드는 Cache에 저장되지 않는다(?)  
설정파일이 필요하다. `ehcache.xml`이라는 이름으로 resource디렉토리에 저장한다면, yml에 `spring.cache.jcache.config: classpath:ehcache.xml` 추가해주도록 하자.  

추후 추가..

---  