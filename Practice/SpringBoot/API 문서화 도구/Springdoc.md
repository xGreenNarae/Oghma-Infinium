
Swagger UI 통합


[property 설정 관련(application.yaml)](https://springdoc.org/#properties)

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
            )            .externalDocs(  
                new ExternalDocumentation()  
                    .description("Springdoc 공식문서")  
                    .url("https://springdoc.org")  
            )            .servers(  
                List.of(  
                    new Server()  
                        .url("http://localhost:8080")  
                        .description("Local Test Server"),  
                    new Server()  
                        .url("https://test-server.com")  
                        .description("Test Server")  
                )            );    }}
```

![[Springdoc Main.png]]

---

