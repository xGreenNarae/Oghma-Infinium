
대략적으로 이런 구조에서 조금씩 바꾸어 사용할 수 있음.

```
public interface Response {
    public static Response success() {
        return new ApiResult<>(true, null, null);
    }

    public static <T> Response success(T response) {
        return new ApiResult<>(true, response, null);
    }

    public static Response error(String message, HttpStatus status) {
        return new ApiResult<>(false, null, new ApiError(message, status.value()));
    }

    @Getter @Setter @AllArgsConstructor
    static class ApiResult<T> implements Response{
        private final boolean success;
        private final T response;
        private final ApiError error;
    }

    @Getter @Setter @AllArgsConstructor
    static class ApiError {
        private final String message;
        private final int status;
    }
}

```  

Controller  
```
public ResponseEntity<Response> method() {
	return ResponseEntity.ok().body(Response.success());
}
```


JSON 형식은 다음과 같다
```
{
	"success": true | false,
	"response": Primitive | Array | Object | null,
	"error": null | {
		"message": String,
		"status": int
	}
}
```