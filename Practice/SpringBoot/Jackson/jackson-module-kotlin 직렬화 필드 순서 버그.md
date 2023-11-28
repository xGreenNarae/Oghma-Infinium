의도한 대로(필드 순서) 직렬화 json이 나오지 않는 경우가 있다.
(생성자 필드, 클래스 필드 모두 존재하는 경우 혹은 sealed class 등 .. [정확히 어떤 문제라고 파악하기 어려운 듯함](https://github.com/FasterXML/jackson-module-kotlin/issues/271#issuecomment-1435809186))

Java에 사용되던 jackson 모듈의 일부가 kotlin -> java 컴파일 과정에 발생하는 내용을 대응하면서 발생하는 문제인 것 같기도 하고..

순서를 보장해야 한다면, 
`@JsonPropertyOrder`를 사용하는 것으로 해결할 수 있는데다가 
많은 경우 순서가 문제가 될 일이 없을 것으로 보고 종료.