
메시지 큐
Advanced Message Queuing Protocol (AMQP)를 사용한다.


**기본**

**5672**
클라이언트와 연결하여 통신하는 포트(AMQP)

**15672**
예를 들어 Docker image중에 `management`라는 태그의 이미지를 사용하면 HTTP를 사용하는 web gui 기반 management 콘솔을 제공하는 포트

**설정파일**
`/etc/rabbitmq/rabbitmq.conf` 디렉토리 내부에 설정파일이 있다.

**데이터 저장**
Mnesia라는 DBMS를 사용하여 데이터를 저장하는데 `/var/lib/rabbitmq/mnesia` 경로에 들어간다.

---
**구성 및 동작** 

**Producer**로 부터 메시지를 받아서,
**Exchange** 라는 과정을 거쳐 적절한 큐에 할당한다.(Routing 개념)

Exchange의 4가지 타입
- Direct: Routing Key가 **정확하게 일치**하는 Queue에게 메시지 전송(Unicast)
- Topic: Routing Key **패턴**이 일치하는 Queue에게 메시지 전송(Multicast)
- Headers: key:value 구성의 **header** 값을 기준으로 일치하는 Queue에 메시지 전송(Multicast)
- Fanout: 해당 Exchange에 등록된 **모든** Queue에 메시지 전송(Broadcast)

**Binding**: exchange로부터 메시지가 어떤 큐에 들어갈지에 대한 규칙.

---

**통신, Ack와 Flow Control**

RabbitMQ 쪽에서 consumer에게 직접 push 한다.
메시지를 처리하는 시간이 길어질수록 한 번에 많이 가져다 두는게 유리할 것이므로, `prefetch` 제한의 설정을 조절한다.

producer로부터 메시지를 잘 전달 받았는지 ACK하는 과정이 있다.

consumer에게, 메시지가 한번 전송되면 메시지가 전달된 것으로 간주하거나(auto-ack), 
consumer가 직접 acknowledgement 처리를 할 때 까지 대기할 수 있다.(일반적)
(헷갈릴 수 있는 부분: consumer가 메시지를 가지고 무슨 처리를 얼마나 하는지가 중요한게 아니라, "메시지를 받았는가"가 중요한것. 이 때 ack를 받는다는 뜻이다.)

consumer로부터 ACK를 받으면 RabbitMQ는 디스크에서 메시지를 삭제한다.

