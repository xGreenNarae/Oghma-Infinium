https://github.com/macosui/macos_ui
UI Kit

앱 시작 전에 이걸 빠뜨리면 배경이 검정이 된다.
```
Future<void> _configureMacosWindowUtils() async {
  const config = MacosWindowUtilsConfig();
  await config.apply();
}

Future<void> main() async {
  if (!kIsWeb) {
    if (Platform.isMacOS) {
      await _configureMacosWindowUtils();
    }
  }

  runApp(const MacosUIGalleryApp());
}
```