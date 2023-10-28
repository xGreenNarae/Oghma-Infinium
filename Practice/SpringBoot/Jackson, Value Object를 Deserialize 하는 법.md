
Custom Object를 Nested Json으로 만들지 않으면서, 가장 적은 비용으로 Deserialize 시키는 방법이다.
```java
public class Money {  
  
    private int amount;  
  
    public Money(final int amount) {  
        this.amount = amount;  
    }  
    public Money add(final Money money) {  
        return new Money(amount + money.amount);  
    }  
  
    @JsonValue  // 이 주석을 사용하면 된다. 클래스에서 유일해야 한다.
    public String json() {
    // 메소드 이름은 toString으로 하는게 좋겠는데, 
    // 다른 이름이어도 상관없다는 것을 보여주기 위함.
        final NumberFormat currency = NumberFormat.getCurrencyInstance();  
        return currency.format(amount);  
    }  
}
```

사용.
```java
@Getter  
public class MoneyDto {  
    private Money money;  

	....
}
```

```JSON
{
    "money": "123,456,000"
}
```