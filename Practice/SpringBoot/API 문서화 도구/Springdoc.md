
Swagger UI 통합


[property 설정 관련(application.yaml)](https://springdoc.org/#properties)
```
springdoc:  
  swagger-ui:  
    enabled: true  
    path: /api/docs  
  
    tags-sorter: alpha # 영숫자 순서로 "태그" 정렬
```



build.gradle
```
// https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-ui  
implementation 'org.springdoc:springdoc-openapi-ui:1.7.0'
```

---

bean 설정
```
import io.swagger.v3.oas.models.ExternalDocumentation;  
import io.swagger.v3.oas.models.OpenAPI;  
import io.swagger.v3.oas.models.info.Info;  
import io.swagger.v3.oas.models.servers.Server;  
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;  
  
import java.util.List;  
  
@Configuration  
public class ApiDocConfiguration {  
  
    @Bean  
    public OpenAPI openAPI() {  
        return new OpenAPI()  
            .info(  
                new Info()  
                    .title("SpringBoot Test Server API")  
                    .description("각종 Local Test를 위한 서버 입니다.")  
                    .version("v0.2.0")  
            ).externalDocs(  
                new ExternalDocumentation()  
                    .description("Springdoc 공식문서")  
                    .url("https://springdoc.org")  
            ).servers(  
                List.of(  
                    new Server()  
                        .url("http://localhost:8080")  
                        .description("Local Test Server"),  
                    new Server()  
                        .url("https://test-server.com")  
                        .description("Test Server")  
                )            
            );    
	}
}
```

![[Springdoc Main.png]]

**GroupedOpenApi, groupd, pathsToMatch 설정으로 end point 별로 페이지를 다르게 나눌 수 있다.


---

**기본적으로는 아무 설정도 하지 않아도 되지만, 이름 등을 설정하려면 추가적인 작업이 필요한데, Controller에 Interface를 사용하는 방법을 생각해 볼 수 있다.

**Response 자동 추가**
```
@RestControllerAdvice  
public class GlobalExceptionHandler {  
    @ExceptionHandler(IllegalArgumentException.class)  
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 적용되는 모든 API(이 예외가 발생할 가능성이 있는)에 자동 Response 추가 해준다.
    public ResponseEntity<?> badRequest(IllegalArgumentException e) {  
        return ResponseEntity.badRequest().build();  
        
    }  
    @ExceptionHandler(Exception.class) // 이 경우, Response Status가 없기 때문에 자동으로 추가되지는 않는다.
    public ResponseEntity<?> internalServerError(Exception e) {  
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  
    }}
```


**Valid 도 자동으로 추가된다.
![[Springdoc Validation.png]]


