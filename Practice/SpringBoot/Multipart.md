#### Multipart  
이미지, 비디오 등 파일 업로드 및 다운로드를 위한..  

```
spring.servlet.multipart.max-file-size=20MB # 개별 파일 사이즈
spring.servlet.multipart.max-request-size=25MB # 총 Request 사이즈 
```  
예외 처리에 대해서는 아래에서 따로 다룸.  


로컬에 저장을 구현할때..  
파일 저장경로를 multipart location 등으로 설정하면 임시폴더에 저장되게 된다. 즉, 프로젝트 폴더의 resources 하위에 저장하는데에는 문제가 따른다는건데.. [참조](https://stackoverflow.com/questions/12160639/what-does-servletcontext-getrealpath-mean-and-when-should-i-use-it)   
그냥 서버환경에 맞춰 절대경로를 설정파일에 저장해두고 사용하도록 하자.(임시구현이라면 D:\ 또는 루트경로 권장. 윈도우 로컬에서 C드라이브 경로사용시 권한주의)  

확장자를 구할때..  
`fileName.substring(fileName.lastIndexOf(".") + 1);`  
직접 잘라내자.  

  
---  

#### Exception 처리에 대해서 ..  

파일사이즈 예외는 `FileSizeLimitExceededException`로 잡을수 있다. 이것은 `Exception.class` 핸들러에게 우선순위를 뺏기는듯 하다.  
  
`MultipartException` 이라는 것도 있고..  

`MaxUploadSizeExceededException`의 경우 fileSize, RequestSize 둘 다를 우선적으로 캐치하게 되는듯. 이것이 그나마 괜찮은 Practice인 듯 하다.

Request Size가 너무 커지면 애초에 서버 레벨에서 받지 않는 경우.. 
```
server.tomcat.max-swallow-size: 10MB

spring.servlet.multipart.max-file-size=2MB
spring.servlet.multipart.max-request-size=5MB 

```
예를들어 다음과 같은경우.  
`MaxUploadSizeExceededException`를 통해, 단일파일 용량초과와 request용량초과를 10MB 이전까지는 예외처리해줄수 있다.  
둘을 구분하여 처리하는 법은 더 찾아보도록 하자.  
10MB를 넘어가면 클라이언트에서는 어떤 오류인지 알 방법이 없다.(연결이 끊김) 그렇다고 max-swallow-size를 늘리는 것은...  
   
---  

#### JSON과 Multipart를 같이 받으려면 ?  
```
@PostMapping(path = "/file-upload-with-json", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public String uploadWithJson(@RequestPart final JsonDto dto,
                                 @RequestPart final List<MultipartFile> files){
        ...
    }
```  

와 같은 예제를 사용할 수 있다.  

request쪽에서는 form-data 로 각각 application/json, image/jpeg 등 content-type을 나누어 보내면 된다.  

---  


#### JUnit Multipart, RequestPart Test

https://blog.paimon.studio/52


