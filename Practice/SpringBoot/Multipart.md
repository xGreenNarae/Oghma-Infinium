#### Multipart  
이미지, 비디오 등 파일 업로드 및 다운로드를 위한..  

```
spring.servlet.multipart.max-file-size=20MB # 개별 파일 사이즈
spring.servlet.multipart.max-request-size=25MB # 총 Request 사이즈 
```  
파일사이즈 예외는 `FileSizeLimitExceededException`,  
Request사이즈 예외는 `SizeLimitExceededException` 라고한다.  

`MultipartException` 이라는 것도..  

...  

