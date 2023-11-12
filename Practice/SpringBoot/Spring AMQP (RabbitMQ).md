
Advanced Message Queuing Protocol
메시지 큐에 사용되는 프로토콜 ..

**RabbitMQ**

서버가 띄워졌다면, 연결 설정을 추가한다.
```application.yml
spring:  
  rabbitmq:  
    host: localhost  
    port: 5672  
    username: user  
    password: password
```

RabbitMQ 설정
```java
package .. 
  
import com.fasterxml.jackson.databind.ObjectMapper;  
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;  
import org.springframework.amqp.core.*;  
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;  
import org.springframework.amqp.rabbit.connection.ConnectionFactory;  
import org.springframework.amqp.rabbit.core.RabbitTemplate;  
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;  
import org.springframework.amqp.support.converter.MessageConverter;  
import org.springframework.beans.factory.annotation.Value;  
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;  
  
@Configuration  
public class RabbitMQConfiguration {  
  
    public static final String QUEUE_NAME = "queueName";  
    public static final String EXCHANGE_NAME = "queueNameExchange";  
    public static final String ROUTING_KEY = "queueName.routingKey";  
  
    private final String rabbitmqHost;  
    private final int rabbitmqPort;  
    private final String rabbitmqUsername;  
    private final String rabbitmqPassword;  
  
    public RabbitMQConfiguration(  
        @Value("${spring.rabbitmq.host}") final String rabbitmqHost,  
        @Value("${spring.rabbitmq.port}") final int rabbitmqPort,  
        @Value("${spring.rabbitmq.username}") final String rabbitmqUsername,  
        @Value("${spring.rabbitmq.password}") final String rabbitmqPassword  
    ){  
        this.rabbitmqHost = rabbitmqHost;  
        this.rabbitmqPort = rabbitmqPort;  
        this.rabbitmqUsername = rabbitmqUsername;  
        this.rabbitmqPassword = rabbitmqPassword;  
    }  
    @Bean  
    public Queue queue() {  
        return new Queue(QUEUE_NAME, false); // 2번째 인자는 durable설정으로, 브로커가 재시작되어도 데이터가 유지되려면 true로 한다.
    }  
    @Bean  
    public Exchange exchange() {  
        return new DirectExchange(EXCHANGE_NAME);  
    }  
    @Bean  
    public Binding binding(final Queue queue, final Exchange exchange) {  
        return BindingBuilder  
            .bind(queue)  
            .to(exchange)  
            .with(ROUTING_KEY)  
            .noargs();  
    }  
    @Bean  
    public ConnectionFactory connectionFactory() {  
        final CachingConnectionFactory connectionFactory = new CachingConnectionFactory();  
        connectionFactory.setHost(rabbitmqHost);  
        connectionFactory.setPort(rabbitmqPort);  
        connectionFactory.setUsername(rabbitmqUsername);  
        connectionFactory.setPassword(rabbitmqPassword);  
        return connectionFactory;  
    }  
    @Bean  
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {  
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);  
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());  
        return rabbitTemplate;  
    }  
    @Bean  
    public MessageConverter jackson2JsonMessageConverter() {  
        return new Jackson2JsonMessageConverter(  
            new ObjectMapper()  
                .registerModule(new JavaTimeModule())  
        );    }}
```


메시지를 발행할 때,
```java

private final RabbitTemplate rabbitTemplate;  

public void publish() {  
    rabbitTemplate.convertAndSend(  
        RabbitMQConfiguration.EXCHANGE_NAME,  
        RabbitMQConfiguration.ROUTING_KEY,  
        MyMessage.builder() // Custom POJO
            ...
            .build()
    );    
}
```

Listener
```java
@RabbitListener(queues = "queueName")  
public void doSomethingWithMessage(final MyMessage message){  
    ....
}
```
작업이 완료되는 즉시, 다음 메시지가 존재한다면 작업을 수행한다.
(pre fetch count 설정할 수 있다.)

메시지 큐로 부터 작업을 이미 가져왔는지, 새롭게 받아들여야하는지는 별개의 이야기 -> RabbitMQ의 작동원리를 참고
