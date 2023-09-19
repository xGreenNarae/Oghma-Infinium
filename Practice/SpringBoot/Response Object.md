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