
JCodec 이라는 라이브러리를 사용하는 예제..  
```
implementation 'org.jcodec:jcodec:0.2.5'
implementation 'org.jcodec:jcodec-javase:0.2.5'
```

MultiPart로 전송받은 파일을 실제 디스크에 저장했다고 치면..  
저장된 파일 경로를 아래의 메소드로 전달한다.  

```
private void extractThumbnail(final String filePath) throws IOException, JCodecException {
        final File file = new File(filePath);
        final FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(file));
        final double startSec = 0; // 영상에서 얻고자 하는 시간대 설정

        grab.seekToSecondPrecise(startSec);
        final Picture picture = grab.getNativeFrame();
        final BufferedImage bfImg = AWTUtil.toBufferedImage(picture);
        ImageIO.write(bfImg, "png", new File(filePath + "-thumbnail.png"));
    }
```