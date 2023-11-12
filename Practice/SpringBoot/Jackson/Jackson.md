
#### DateTime 이 배열로 나오는 문제
`@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")` 붙여준다  
Date의 경우 당연히 yyyy-MM-dd 까지..  

---

#### Controller Response Content-type
ObjectMapper 등을 이용하든지로.. String type 을 return 하면 기본적으로 Content-type 이 text/html 이다.  

---

#### RequestBody 에 들어가는 Dto 의 Field 가 1개일때는 zero-parameter 생성자가 없을 경우 오류가 발생한다.
- Jackson 에 연관된 사항으로, 좋은 이야기가 이미 있다.(제대로 읽어보지는 않음) 필드가 하나인 경우 굳이 객체를 사용할 이유가 없기 때문이 아닐까라고 생각해봤다. 이것 하나를 해결하기 위해서 여러가지 다른 문제를 일으킬 여지들이 많다는 뉘앙스 인 듯..
  - https://github.com/mock-rc4/coupangEatsB-test-server-ashley/issues/15
- 내가 선택한 해결책은 그냥 zero-parameter 를 추가해서 사용하는것. 또는 Dto 의 경우 Setter 를 사용해도 괜찮을 것 같다.  

---

#### Java 8 date/time type `java.time.LocalDateTime` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310  

Jackson time 직렬화/역직렬화 문제.  

SpringBoot Controller에서 Object를 return할 때는 발생하지 않으나..
테스트 코드 등, objectmapper를 수동으로 생성해서 사용할 때 주로 발생..

`'com.fasterxml.jackson.datatype:jackson-datatype-jsr310` 종속성을 추가하여 해결하거나,  
`new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString("DTO Object..");`  
와 같이 해결할 수 있음.  
테스트 코드라면 아래쪽을 만들어두고 상속 등으로 공유해서 사용하거나 하자..

---  
