#### Valid 의 순서가 보장되지 않는이유  
선언된 제약조건들이 ConcurrentHashMap 이라는 자료구조에 저장 - 접근 되기 때문에 매 요청마다 검사가 무작위로 발생하는 것으로 보이는데... 

--- 

#### Logback 에 DBAppender 가 어느버전인가부터 사라진 것 같다.
직접 구현하라는 것 같은데, 어째서 일까.  
A: Logback 1.2.8 버전부터 DBAppender가 제거되었다.[Issue Link](https://jira.qos.ch/browse/LOGBACK-1609)  
보안취약점의 문제로, 해결이 될 경우 다시 지원될 수 있다고 함.    


---  

#### HikariCP 가 Connection을 빌려줄때, 왜 이전 사용기록을 참조하여 가능하면 같은것을 빌려주려고 하는가?(그리고 이렇게 하고있는것이 맞긴 한가?)  

---  

