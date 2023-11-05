
프로그램 레벨의 구현이 아닌, OS 레벨에서 지원한다.

`fcntl(sockfd, F_SETFL, O_NONBLOCK);` 이런식.


Java의 경우 `java.nio` 에서 지원된다.

```
// 서버 소켓 채널 열기 
ServerSocketChannel serverSocket = ServerSocketChannel.open(); 

InetSocketAddress hostAddress = new InetSocketAddress("localhost", 8080); 

// 논블로킹 모드 설정 
serverSocket.configureBlocking(false);
```
대충 이런식.


