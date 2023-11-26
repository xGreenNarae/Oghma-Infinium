
#### 자주하는 실수 모음

**invalid constant value**
대부분 위젯 앞에 const 키워드를 제거하라는 뜻.

**Column, Row, ListView .. Expanded**
가로, 세로 또는 모든방향으로 가질 수 있는 최대의 너비를 선언한다.
따라서 이런 위젯이 중첩될 경우 사용 가능한 너비를 정의하는데 오류가 발생할 수 있음. 
shrinkWrap등의 속성은 실제 사용하고 있는 너비만 선택하게 할 수 있다.
SizedBox.expand 라는 위젯도 있다.

**위젯의 생성, 제거 등 수명주기에 동작이 필요하면 Stateful Widget을 사용한다**
`onInit, onDispose ..`


---

**내가 사용하는 GetX**
아마도 가장 짧고 간단한 구현을 만들어 낸다.
몇 페이지 분량을 넘어설 정도로 복잡해지거나, 신뢰성 있는 제품이 필요한 경우 다른 기술이 권장되는 듯 함.
개인용 앱으로는 부족함이 없는 것 같다.


- 페이지 라우팅
```
enum AppRoutes {  
  initial(path: '/');
  
  const AppRoutes({required this.path});  
  
  final String path;  
}  
  
class AppPages {  
  static final routes = <GetPage>[  
    GetPage(  
      name: AppRoutes.initial.path,  
      page: () => const Home(),  
      binding: MyControllerBinding(), // GetxController의 의존성관리
    ),
  ];
}
```

- GetxController를 위젯(페이지)에 바인딩
```
class MyBinding extends Bindings {  
  @override  
  void dependencies() {  
    Get.lazyPut<MyController>(() => MyController());  
	// 또는 그냥 Get.put..
  }
}
```



- 페이지 제거 등
`Get.back(closeOverlays: true);`
closeOverlays 속성은 열려있는 snack bar 등을 같이 제거한다.

- snackbar, dialog .. 등을 사용할 수 있다.


- Rx, Obs, Obx(Reactive Widget)
GetxController의 변수로 Rx 타입을 만들어두면,
UI에서 해당 변수를 참조 중인 영역을 Observable 하게 만들 수 있다.

view에서 Obx를 사용해서 FutureBuilder등 을 간단하게 대체할 수 있다..


`Rx<Widget>, RxList<Widget>` 타입의 필드는 빈 값으로 필드 초기화 해서 쓰는 것이 간단한 프랙티스로 보임.
`RxList<Character> characters = <Character>[].obs;`
```
final Rx<Character> character = Character(  
  name: "",  
  properties: Property(  
    key1: [],  
    key2: [],  
  ),
).obs;
```


