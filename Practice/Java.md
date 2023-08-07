#### Lazy Stream Filter
stream 문법에서, predicate 다음에 filter 가 chaining 되기 때문에 predicate 가 filter의 조건을 만족하지 않아도 항상 실행된다고 생각할 수 있다. 하지만 내부 코드에는 predicate 를 일단 저장하고, filter 를 만족하는 경우에만 실행한다.

