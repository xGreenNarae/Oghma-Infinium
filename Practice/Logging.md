#### 로그를 어떻게하면 잘 남길수 있을까  
- 포맷팅, 모듈화 - 용도별 분리
- 개행을 가능하면 하지 않는 것이 좋겠다
- 로거를 다양하게 만들어두고 동적으로 on/off 시켜둔다. 이 때, 수가 적으면 레벨을 조정하기 어렵다
- trace 로그는 특수 디버깅용.
- 로그가 찍히게 되는 상황을 유추할 수 있는 정보.

이렇게 할 것.

- 누가 실패한 것인지 (User, Client Ip 등 ..)
- 레벨을 명확히 (Error ..)
- “값” 의 의미를 반드시 함께 기록 ( {userId}, {dataId} 와 같이하면 1, 3 같은 숫자의 나열이 되어버림 )
- 해당 상황의 원인이 되는 값을 함께 기록. (parameter 등)
- 매우 빈번하게 호출되는 함수에서는 로깅으로 인한 성능이슈에 주의한다. 특히 주기적으로 반복호출되는 함수 등 → 그냥 하지말것?
- 로그는 한 줄에 작성, 줄바꿈 문자도 쓰지 않는다.
- 시스템 시간에 의존하지 않도록 한다.

상황, 맥락을 담는 정보란

- 현재 실행중인 함수를 식별
- 누가 호출했는지
- 예외 발생 원인 (DB에러, 특정로직에러 등..)
- 상황의 원인이 되는 키 변수가 있다면 반드시 기록
- 로깅할 변수가 NullPointer 가 될 상황이 없는지 주의
- 이 로깅이 발생함으로써, 이후에 벌어질 일 들을 함께 기록.