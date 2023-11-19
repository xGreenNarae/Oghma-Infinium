https://isar.dev/ko/
e zar 정도로 발음한다고 함. 베를린의 어떤 강 이름이라고 한다.

Flutter를 위한 임베디드 데이터베이스(파일로 데이터를 저장)

join을 사용하지 않는 문서 기반 NoSQL이고, 간단한 ORM QueryBuilder 형식의 문법을 제공한다.

collection을 작성하고 나면 `dart run build_runner build` 라는 커맨드로 스키마 파일을 생성해줘야 한다.(코드생성)
이 때, `model.dart` 라는 파일에 컬렉션이 정의되어있다면, 
`part model.g.dart` 구문을 추가해두고,
`model.g.dart`라는 이름의 빈 파일을 미리 만들어 줘야 동작했다.(문서에는 없는 내용)

또한 windows build 이후, .exe 파일이 제대로 실행되려면 `isar` 인스턴스가 참조하고 있는 데이터파일 저장 경로를 .exe 파일이 있는 곳에 만들어줘야 한다.
(그렇지 않으면 백그라운드 프로세스만 실행되고 창이 뜨지 않음)



