
#### 자주하는 실수 모음

**invalid constant value**
대부분 위젯 앞에 const 키워드를 제거하라는 뜻.

**Column, Row, ListView .. Expanded**
가로, 세로 또는 모든방향으로 가질 수 있는 최대의 너비를 선언한다.
따라서 이런 위젯이 중첩될 경우 사용 가능한 너비를 정의하는데 오류가 발생할 수 있음. 
shrinkWrap등의 속성은 실제 사용하고 있는 너비만 선택하게 할 수 있다.
SizedBox.expand 라는 위젯도 있다.


