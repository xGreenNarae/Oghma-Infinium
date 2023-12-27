
`-` 기호의 유무가 어려운 부분이다.


`-` 기호가 없는 나열은 key-value 구조다.(dict, json object) 
```yml
p1:
	c1: content
	c2: content
```
```json
{
	p1: {
		{ c1: content }
		{ c2: content }
	}
}
```


`-` 기호와 함께하는 나열은 list 구조다.(array)
```yaml
- p1:
	- c1: content
	  c2: content
	- c3: content
- p2:
```
```json
[
	{
		p1: [
			{
				c1: content,
				c2: content
			},
			{
				c3: content
			}
		]
	},
	{
		p2: None
	}
]
```


둘은 하나의 레벨에서 공존할 수 없다.
```yaml
p1:

- p2:
```
위는 문법 오류다.


예제
```
project/:  
  - c1: "content"  
    cc1: "content"  
  - c2: "content"  
    cc2: "content"  
  - c3: "content"  
    cc3: "content"  
  - subdir/:  
  
  
dir2/:  
  subdir1/:  
    - c4: "content"  
      cc4: "content"  
  
dir1/:
```
```json
{
   "project/":[
      {
         "c1":"content",
         "cc1":"content"
      },
      {
         "c2":"content",
         "cc2":"content"
      },
      {
         "c3":"content",
         "cc3":"content"
      },
      {
         "subdir/":"None"
      }
   ],
   "dir2/":{
      "subdir1/":[
         {
            "c4":"content",
            "cc4":"content"
         }
      ]
   },
   "dir1/":"None"
}
```