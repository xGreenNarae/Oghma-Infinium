
옵저버 패턴으로 구현되는 Event Driven (publish/notify) 방식의 객체 간 메시지 전달 기술의 사용법 정리


Event는 Dto등 과 같은 값을 가진 객체면 된다.
```
public record MessageEvent (  
        String sender,  
        String receiver,  
        String message,  
        LocalDateTime publishedAt  
) { }
```

이벤트 발행은 `ApplicationEventPublisher`를 사용한다.
`FunctionalInterface`로 `publishEvent` 밖에 없다.
```
@Service  
@RequiredArgsConstructor  
public class MessageService {  
  
    private final ApplicationEventPublisher applicationEventPublisher;  
  
    public void sendMessage(final MessageDto messageDto) {  
        applicationEventPublisher.publishEvent(MessageEvent.fromDto(messageDto));  
    }}
```

수신은 `@EventListener` 가 붙어있어야 한다.
```
@Slf4j  
@Service  
@RequiredArgsConstructor  
public class MessageStoreService {  
  
    private final MessageEntityRepository messageEntityRepository;  
  
    @EventListener  
    public void storeMessage(final MessageEvent messageEvent) {  
        messageEntityRepository.save(MessageEvent.toEntity(messageEvent));  
        log.info("Message Stored!: {}", messageEvent);  
    }  
}
```

**@EventListener**
속성은 `value(classes)`, `condition`, `id`

classes 속성으로 여러 이벤트를 명시할 수 있으나, 이 경우 파라미터를 가지면 안된다고 함.
- 따라서 파라미터가 하나인 리스너를 사용하고, 단일 이벤트를 파라미터로 수신하여 처리하자.

- 한 이벤트에 대한 리스너들의 순서를 지정하려면 `@Order` 사용한다.

- Return value를 가질 수 있는데... 이 경우 새로운 이벤트를 publish 하는 것으로 동작한다고 한다. 즉, 자기가 수신한 이벤트와 동일한 타입의 이벤트를 Return하게 되면 무한 재귀 호출에 빠진다. 


**비동기 처리**

어디선가 `@Configuration` 등으로 `@EnableAsync` 설정해줘야 한다고 함.
이벤트리스너 메소드에 `@Async` 붙여주도록 한다.
```
@Async  
@EventListener  
public void logMessage(final MessageEvent message) {  
    log.info("Message received: {}", message);  
  
}
```
비동기이기 때문에, 당연히 `@Order`는 더 이상 먹히지 않는 듯함.
비동기 방식에서는 예외가 호출자에게 전파되지 않는다고 한다.

---
#### @TransactionalEventListener

이벤트 발행자가 `@Transactional` 등의 상황일 때,
해당 트랜잭션의 상태에 따라 이벤트를 처리하는 것.
그러니까, 이벤트 수신자는 발행자의 트랜잭션 상태를 함께 받아서 이벤트 처리여부를 조작한다는 느낌이다.

**@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)**
트랜잭션이 commit 되기 전에 이벤트를 실행합니다.

**@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)**
default 값이며, 트랜잭션이 commit 되었을 때 이벤트를 실행합니다.

**@TransactionalEventListener(phase = TransactionPhase.ROLLBACK)**
트랜잭션이 rollback 되었을 때 이벤트를 실행합니다.

**@TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)**
트랜잭션이 completion_(commit 또는 rollback)_ 되었을 때 이벤트 실행합니다.

주의할 점은, 이벤트 수신자가 발행자의 트랜잭션과 연관되는 작업(Insert 등)을 해야한다면, `Propagation.REQUIRED_NEW` 등의 중첩 트랜잭션 사용해야하고.... 가능하다면 복잡하게 만들지 말자.

