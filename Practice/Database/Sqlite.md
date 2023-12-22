
Embedded DB 중 에서는
범용 목적으로 사용하기에 가장 좋아 보인다.

**Data Type**은 5개가 있다
NULL 
INTEGER(1,2,3,4,6,8바이트)
REAL(8바이트 부동소수점)
TEXT
BLOB

동적 타입 시스템을 사용하고, Integer 타입으로 지정한 곳에 TEXT를 넣을 수도 있다.(강제하지 않는다는 뜻)
마찬가지로 컬럼에 데이터타입을 필수로 지정하도록 하지 않는다.

`sqlite3` 는 sqlite에 대한 인터페이스 -프론트엔드- 를 의미한다. python같은 경우, 내장되어있다.


**mysql과 sql구문에서 몇 가지 차이점**
auto increment 관련
특수문자 처리

또한,
"쓰기 작업 시 데이터베이스 전체가 잠긴다"





