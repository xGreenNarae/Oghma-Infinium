
예를들어, Batch작업 또는 외부 API 호출 등 여러가지 요인에 의해 실패할 수 있는 메소드들을 재시도하는 로직을 편리하게 사용하도록 지원한다.

`implementation 'org.springframework.retry:spring-retry'`


간단한 예제
```
@RestController  
@RequiredArgsConstructor  
public class RetryController {  
    private final RetryService retryService;  
  
    @GetMapping("/retry")  
    public int retry() {  
  
        return retryService.retryMethod(7);  
    }
}
```

```
@Slf4j  
@Service  
public class RetryService {  
  
    private final Random random = new Random();  
  
    @Retryable(  
        value = {ClientException.class}, // 특정 Exception이 발생했을 경우에만 Retry 한다. 기본값은 empty        maxAttempts = 3, // 최대 3번 Retry        backoff = @Backoff(delay = 1000), // 1초 간격을 두고 Retry        // include = ClientException.class, 위의 value와 동일한 속성임에 주의! (Synonym)  
        recover = "recover" // 동일한 Recover이 여러개일때, 특정 Recover method를 지정할 수 있다. 지정하지 않을 경우 내부 구현에따라 우선순위가 있는 듯..  
    )  
    public int retryMethod(final int max) {  
        final int randomNumber = this.random.nextInt(max);  
        log.debug("randomNumber: {}", randomNumber);  
  
        if (randomNumber < 5) throw new ClientException("5보다 작은 숫자가 나왔습니다.");  
  
        return randomNumber;  
    }  
    @Recover // annotation 필수  
    private int recover(final ClientException e, final int max) { // Exception 파라미터 필요, 그리고 이외 메소드 시그니처가 동일해야함.  
        log.warn("RetryException 발생 : {}", e.getMessage());  
        throw e;  
    }}
```

주의할 점은,  같은 클래스 내에서 `@Retryable` 을 달고있는 method를 호출한다고 해서 Retry가 동작하지는 않는다는 것이다. `@Transactional` 등과 같은 이유.

지정된 `@Recover` method가 메소드 시그니처가 다르거나, 적절한 Recover method를 찾을 수 없다면 `ExhaustedRetryException` 발생한다.