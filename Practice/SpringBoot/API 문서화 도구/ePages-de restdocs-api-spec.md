
## 현재 단점으로, SpringBoot 2.x 와 호환되는 버전의 경우, 파일 업로드에 대한 Swagger Test 를 지원하지 않는다.(multipart-form-data, requestPart)


#### Api 문서화에 대해서  
Swagger와 REST Docs라는 기술을 사용할 수 있는데,  
각 장점들을 합치고, 단점들을 해소한 구현체가 있다.  
[GitHub 링크] (https://github.com/ePages-de/restdocs-api-spec)  

다음과 같은 장점들이 있다.

- Swagger의 장점인 **테스트 가능한 API문서**
- RestDocs의 장점(단점으로 해석할 수도 있지만..)인 **문서의 신뢰성**
    - 테스트가 완료된 API 에 대해서 만 문서 제공.
- **문서화의 완전 자동화**
    - Swagger와 RestDocs가 모두 가지고 있던 API가 추가될 때마다 유지 보수 되어야 한다는 단점들을 해결
        - Swagger: Annotation들이 어딘가에 추가되어야 한다.
        - RestDocs: AsciiDoc에 대한 추가 수정이 필요
- Swagger의 단점으로 여겨지던 Application의 소스 코드에 문서화 코드가 침투하는 문제 또한 해결.
- AsciiDoc 문법을 이해하고 외우고 있어야 하는 비용을 없앤다.



MockMvc를 사용하는 구현 예시는 다음과 같다.  

spring yml 설정
```
springdoc:
  default-consumes-media-type: application/json;charset=UTF-8
  default-produces-media-type: application/json;charset=UTF-8
  swagger-ui:
    url: /docs/openapi3.json
    path: /api/docs/api-docs # api doc path
```

build.gradle 수정
```
buildscript {
	ext {
		restdocsApiSpecVersion = '0.16.2'
	}
}

plugins {
	...
	id 'com.epages.restdocs-api-spec' version "${restdocsApiSpecVersion}"
	id 'org.hidetake.swagger.generator' version '2.19.2'
}


// dependency  
testImplementation 'org.springframework.restdocs:spring-restdocs-mockmvc'
testImplementation "com.epages:restdocs-api-spec-mockmvc:${restdocsApiSpecVersion}"

openapi3 {
	server = "http://localhost:8080" // List 로 넣을수 있음.
	title = "..."
	description = ""
	version = "0.1.0"
	format = "json"
	outputDirectory = 'build/resources/main/static/docs'
}


tasks.withType(GenerateSwaggerUI) {
	dependsOn 'openapi3'

	// local 확인
	delete 'src/main/resources/static/docs/*'
	copy {
		from "build/resources/main/static/docs"
		into "src/main/resources/static/docs/"
	}
}
```

ApiDoc 생성을 위한 JUnit 테스트에 부모 클래스를 만들어두었음.  
```
@AutoConfigureRestDocs(uriScheme = "http")
@AutoConfigureMockMvc
@ExtendWith({RestDocumentationExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public abstract class ApiDocTest {

  @Autowired
  protected WebApplicationContext ctx;

  @Autowired
  protected ObjectMapper om;

  @Autowired
  protected MockMvc mvc;

  protected ResultActions resultActions;

  @BeforeEach
  void setUp(final RestDocumentationContextProvider restDocumentation) {
    mvc = MockMvcBuilders.webAppContextSetup(ctx)
      .apply(documentationConfiguration(restDocumentation))
      .addFilters(new CharacterEncodingFilter("UTF-8", true))
      .alwaysDo(print())
      .build();
  }

  @AfterEach
  void generateApiDocAfterTest(TestInfo testInfo) throws Exception {
    String className = testInfo.getTestClass().isPresent() ?
      testInfo.getTestClass().get().getSimpleName() : "";

    generateApiDoc(resultActions,  className);
  }

  protected void generateApiDoc(ResultActions resultActions, String className) throws Exception {
    resultActions.andDo(MockMvcRestDocumentationWrapper
      .document("{class-name}/{method-name}",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint()),
        resource(
          ResourceSnippetParameters.builder()
            .description(className)
            .requestFields()
            .responseFields()
            .build()
        )
      )
    );
  }
  
  // resultActions 반복을 없애기 위한 ...

  protected void expectSuccess() throws Exception {
    resultActions.andExpect(jsonPath("$.success").value("true"))
      .andExpect(jsonPath("$.error").isEmpty());
  }

  protected void expectFail(String message, int status) throws Exception {
    resultActions.andExpect(jsonPath("$.success").value("false"))
      .andExpect(jsonPath("$.response").isEmpty())
      .andExpect(jsonPath("$.error.message").value(message))
      .andExpect(jsonPath("$.error.status").value(status));
  }

  // 메시지 검증이 귀찮음.
  protected void expectFail(int status) throws Exception {
    resultActions.andExpect(jsonPath("$.success").value("false"))
      .andExpect(jsonPath("$.response").isEmpty())
      .andExpect(jsonPath("$.error.status").value(status));
  }
}
```

이것을 상속하여 다음과 같이 사용하였음.  
```
class UserApiDocTest extends ApiDocTest {
  private String existEmail = "abcd@nate.com";
  private String nonExistEmail = "anonymous@gmail.com";
  private String existPassword = "abcd1234!";

  @Nested
  class 이메일_중복체크 {
    @Test
    void 성공_이메일중복체크() throws Exception {
      UserRequest.EmailCheckDTO requestDTO = new UserRequest.EmailCheckDTO();
      requestDTO.setEmail(nonExistEmail);

      resultActions = mvc.perform(
        post("/check")
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsString(requestDTO)));

      expectSuccess();
    }
  }
}
```

Nginx 환경에서 Swagger가 뜨지 않는 경우..  
다음과 같은 것들을 알아본다.  
server.servlet.forward-headers-strategy: framework  

쿠버네티스 환경이라면..  
nginx.ingress.kubernetes.io/x-forwarded-prefix: '/{url}/api'  # yaml 부분 설정..  
Swagger Api test url 이 build.gradle 에 들어가야함.  

---  

#### 조금 더 자세한 사용 가이드
![[Epages API Doc Header.png]]

![[Epages API Doc Example 1.png]]
privateResource 의 역할은 모르겠음
회색 처리 된 것과 Deprecated 표시 등 모두 deprecated 설정에 의한 것이다

---

#### 주의할 점
- id 등이 다르더라도, 동일한 url에 parameter 등이 같으면 같은 것으로 취급되는 듯.
- summary와 description은 하나의 URL 당 하나인 듯. id가 달라서 example을 바꿔서 클릭해도 변하지 않는다..
- 현재 단점으로, SpringBoot 2.x 와 호환되는 버전의 경우, 파일 업로드에 대한 Swagger Test 를 지원하지 않는다.(requestPart)

---
#### 다른 예시

[참조 블로그](https://velog.io/@hwsa1004/Spring-restdocs-swagger-%EA%B0%99%EC%9D%B4-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0#4-%EC%82%BD%EC%A7%88%EA%B8%B0)
```
@DisplayName("소셜 로그인 API")
    @Test
    void socialLogin() throws Exception {
        // given
        // 생략..

        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/auth/signin")
                                .param("code", "JKWHNF2CA78acSW6AUw7cvxWsxzaAWVNKR34SAA0AZ")
                                .param("platform", "KAKAO")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("socialLogin",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("User API")
                                .summary("소셜 로그인 API")
                                .formParameters(
                                        parameterWithName("code").description("발급받은 인가코드"),
                                        parameterWithName("platform").description("플랫폼 : 'GOOGLE' / 'KAKAO' "))
                                .responseFields(
                                        fieldWithPath("code").type(NUMBER).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("상태 메세지"),
                                        fieldWithPath("data.userId").type(NUMBER).description("유저 ID"),
                                        fieldWithPath("data.email").type(STRING).description("유저 이메일"),
                                        fieldWithPath("data.nickName").type(STRING).description("유저 닉네임"),
                                        fieldWithPath("data.profileImageUrl").type(STRING).description("유저 프로필 이미지"),
                                        fieldWithPath("data.accessToken").type(STRING).description("액세스 토큰"),
                                        fieldWithPath("data.refreshToken").type(STRING).description("리프레쉬 토큰"))
                                .requestSchema(Schema.schema("FormParameter-socialLogin"))
                                .responseSchema(Schema.schema("UserResponse.Login"))
                                .build())));

```



---
---
---
