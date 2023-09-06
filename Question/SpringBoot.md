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

#### Jackson 이름의 유래   
Dr.Jackson에 의하면,  
Json과 발음이 유사한것을 이용해 Jason이라고 지어질 예정이었으나, 친구 중에 Jason이라는 이름을 가진 사람이 있었고 이것을 'creepy'하게 느낄것이라 생각하여 Jackson 이라고 했다고 함. 추가적으로, 이름을 짓는것에는 'Super Good Answer'란 없다고 한다.  


