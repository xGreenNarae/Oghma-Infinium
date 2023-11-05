
입력 즉, Json -> Dto로 Deserialize 되는 과정에서
Enum타입을 사용했을 경우, 딱 들어맞는 한 가지 값이 아닌 여러가지 값을 유연하게 매핑하고 싶은 경우가 있다.

예를 들자면, "제주", "제주특별자치도", "제주도"가 모두 제주 라는 이름의 ENUM에 매핑되게 하고 싶다.


`@RequestBody` 의 Json 값을 처리하려면 `@JsonCreator`를 사용한다.
```
@Slf4j  
public enum Province {  
    서울,  
    부산,  
    대구,  
    인천,  
    광주,  
    대전,  
    울산,  
    세종,  
    경기,  
    강원,  
    충북,  
    충남,  
    전북,  
    전남,  
    경북,  
    경남,  
    제주;  
  
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING) 
    public static Province fromJson(
    @JsonProperty("province") final String province) {    
        return switch (province) {  
            case "서울특별시", "서울시", "서울" -> 서울;  
            case "부산광역시", "부산시", "부산" -> 부산;  
            case "대구광역시", "대구시", "대구" -> 대구;  
            case "인천광역시", "인천시", "인천" -> 인천;  
            case "광주광역시", "광주시", "광주" -> 광주;  
            case "대전광역시", "대전시", "대전" -> 대전;  
            case "울산광역시", "울산시", "울산" -> 울산;  
            case "세종특별자치시", "세종시", "세종" -> 세종;  
            case "경기도", "경기" -> 경기;  
            case "강원특별자치도", "강원도", "강원" -> 강원;  
            case "충청북도", "충북" -> 충북;  
            case "충청남도", "충남" -> 충남;  
            case "전라북도", "전북" -> 전북;  
            case "전라남도", "전남" -> 전남;  
            case "경상북도", "경북" -> 경북;  
            case "경상남도", "경남" -> 경남;  
            case "제주특별자치도", "제주도", "제주" -> 제주;  
  
            default -> throw new IllegalArgumentException("Unknown Province value: " + province);  
        };    }  
  
}
```

`JsonCreator.Mode.DELEGATING` 부분이 없으면 자세히는 모르겠지만, Nested Json Object 속에 담겨서 들어올 때, 오류가 발생하는 듯.

자세히 알고 싶다면 아래 이슈를 찾아보자.
https://github.com/FasterXML/jackson-module-kotlin/issues/336  



`@RequestParam` 입력에 대응하려면 `FormatterRegistry`에 `Converter`를 추가해야한다. (아마도 Jackson을 거치지 않음)
```
@Configuration  
public class EnumConverterConfiguration implements WebMvcConfigurer {  
    @Override  
    public void addFormatters(final FormatterRegistry registry) {  
        registry.addConverter(new ProvinceConverter());  
    }
}
```

```
import org.springframework.core.convert.converter.Converter;  
  
public class ProvinceConverter implements Converter<String, Province> {  
  
    @Override  
    public Province convert(final String source) {  
        return Province.fromJson(source);  
    }
}
```

