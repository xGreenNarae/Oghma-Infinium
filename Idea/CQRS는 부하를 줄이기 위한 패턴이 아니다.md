읽기와 쓰기 명령을 분리하자는 것이 주 목적.  
단순히 읽기와 쓰기를 위한 DB를 분리해두고 복제시켜두는 것으로는 쓰기트래픽이 그대로 읽기저장소에 전달되므로..  
주로 이벤트 소싱 등을 같이 도입하고.. 확장성이나 관심사의 분리 등의 이점을 노릴 수 있다.  

성능 향상의 목적으로 읽기, 쓰기 쿼리를 서로 분리해두는 것을 Query-off 라고 부르는 듯 한데..  
데이터베이스를 복제하는것은 읽기 처리량을 향상시키고, 쓰기 처리량향상이 필요하다면 샤딩이 필요하다.  


예시 구조로,  
쓰기 모델은 RDBMS에 저장하고, 저장이 발생하는 시점에 이벤트를 통해 읽기 저장소에 반영할 수 있음. 읽기 모델이 사용하는 저장소의 경우 NoSQL을 사용할 수 있겠다.  


