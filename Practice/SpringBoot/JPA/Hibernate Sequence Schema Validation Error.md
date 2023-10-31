
ddl-auto를 validate로 해두면 entity와 db의 스키마 구조 정합성을 검사해준다.

그 오류 중에, `org.hibernate.tool.schema.spi.SchemaManagementException: Schema-validation: missing table [hibernate_sequence]` 라는게 있다.

보통 entity 파일이 변경되면 ddl 생성 등의 도구를 사용해서 스크립트를 만들고 DB에 적용할 수 있는데, MySQL의 경우 id에 사용되는 `strategy = GenerationType.IDENTITY` 을 생략하게 되면 validation이 실패한다.

참고로 기본 값은 Auto이기 때문에, 항상 실패하는 것인지는 모르겠다.