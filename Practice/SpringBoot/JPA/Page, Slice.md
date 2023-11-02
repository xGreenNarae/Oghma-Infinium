

#### Page, Slice

Page는 count 쿼리가 나간다. 전체가 몇 페이지인지, 현재 몇 페이지인지(offset)

Slice는 얻고자 하는 사이즈의 +1 만큼 쿼리를 날리고, 결과값의 크기가 원하던 사이즈보다 크다면(완전 일치로하면안된다. 그보다 적은 수가 나올수있어서) hasNext값이 true라고 보는 것.

동작 알아야 한다. 
JPA Query method의 지원을 벗어나서 구현해야 할 경우, 직접 구현해야 한다.


---
#### Controller Pageable parameter 
Controller parameter 에서 Pageable객체를 직접 받을 수 있는데..  

```
@GetMapping('/api')
    public Response<...> method(Pageable pageable) {
        return ... ;
    }
```    

요청 파라미터가 다음과 같은 형식이 된다.  
`/api?page=0&size=3&sort=id,desc&sort=username,desc`  

글로벌 설정으로 `spring.data.web.pageable.default-page-size=20`  
개별메소드 설정으로는 다음과 같은..  
```
public String list(@PageableDefault(size = 12, sort = “username”,  direction = Sort.Direction.DESC) Pageable pageable) {
    ... 
} 
```  

Page parameter 가 둘 이상일 경우 Qualifier를 사용한다.  
```
public String list(@Qualifier("member") Pageable memberPageable, @Qualifier("order") Pageable orderPageable, ...){
    ...
}
```  
`/members?member_page=0&order_page=1`  


Page객체를 그대로 Controller에서 응답할 경우, 다음과 같은 속성들이 있다..  
```
{
    "content": [
        {
            "id": 991,
            "name": "name990",
            "email": "email990"
        },
        {
            "id": 992,
            "name": "name991",
            "email": "email991"
        },
        ... 
    ],
    "pageable": {
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 990,
        "pageNumber": 99,
        "pageSize": 10,
        "paged": true,
        "unpaged": false
    },
    "last": true, // 마지막 페이지인지
    "totalPages": 100,
    "totalElements": 1000,
    "size": 10,
    "number": 99,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "first": false, // 첫 페이지 인지
    "numberOfElements": 10,
    "empty": false
}
```


#### Custom Slice 예제
Controller에서 Pageable을 parameter로 입력 받고, jpa repository를 호출하여 slice를 가져온 뒤, 필요한 필드만 추출하여 dto로 응답하는 예제

```
# controller

@GetMapping("/")  
public SliceTestDto sliceTestDto(final Pageable pageable) {  
  
    return SliceTestDto.of(sliceTestEntityRepository.findSliceBy(pageable));  
}
```

```
# custom slice base object

@Getter  
public abstract class RefinedSlice {  
  
    protected int pageNumber;  
    protected int size;  
    protected boolean hasPrevious;  
    protected boolean hasNext;  
  
    protected RefinedSlice(final Slice<?> slice) {  
        this.pageNumber = slice.getNumber();  
        this.size = slice.getSize();  
        this.hasPrevious = slice.hasPrevious();  
        this.hasNext = slice.hasNext();  
    }}
```

```
# dto

@Getter  
public class SliceTestDto extends RefinedSlice {  
  
    private List<TestDto> testDtos;  
  
    private record TestDto(Integer id, String name) {}  
  
    @Builder  
    private SliceTestDto(final Slice<?> slice, final List<TestDto> testDtos) {  
        super(slice);  
        this.testDtos = testDtos;  
    }  
    public static SliceTestDto of(final Slice<SliceTestEntity> slice) {  
        return SliceTestDto.builder()  
            .slice(slice)  
            .testDtos(slice.getContent().stream()  
                .map(entity -> new TestDto(entity.getId(), entity.getName()))  
                .toList())  
            .build();  
    }}
```

