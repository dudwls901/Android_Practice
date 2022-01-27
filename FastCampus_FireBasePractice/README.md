![FastCampus_FirebasePractice](https://user-images.githubusercontent.com/66052467/151383051-cf35202a-697c-4465-9220-d90ae3b9a4d9.gif)
# 파이어베이스 알림 예제
## Stack

- Firebase 토큰 확인
- (FCM)Firebase Cloud Messaging
- Notification(푸시 알림)
- FCM 데이터 메시지
- enum class
## 기능
- 사진 추가 기능(로컬 갤러리 접근)
- 추가한 사진 액자로 띄우고 자동 넘김
- 저장소 접근 권한 팝업(권한에 대한 팝업 + 권한 얻는 팝업)


---
## Firebase
### Firebase?
- 모바일 앱, 웹 어플리케이션을 보다 쉽게 만들고 운영하기 위한 플랫폼
- 서버 -> FCM Backend -> platform_level messageTransport(Android Transport Layer(ATL)) -> SDK

### Firebase 프로젝트
- 파이어베이스를 이용하기 위한 최상위 항목
- 프로젝트 만들고 그 안에 Android, iOS, Web 등을 등록
- 기본적으로 구글 클라우드에 속해서 생성시 내부적으로 구글 프로젝트도 생성됨
- 한 프로젝트에 속한 모든 앱은 최종 사용자의 관점에서 동일한 애플리케이션의 플랫폼 변형 버전이어야 함
- google-service.json은 앱에 프로젝트를 연동할 수 있는 유니크한 정보를 가진 파일
  - 깃헙 업로드 x 로컬 관리, 따로 직접 공유  

### FCM(Firebase Cloud Messaging)?
무료로 메시지를 전송할 수 있는 교차 플랫폼 솔루션으로, 푸시 알림 대부분 FCM 활용

### Notification (푸시 알림)
- 푸시 알림으로, 파이어베이스 콘솔을 통해 알림 메시지 전송 가능, 마케팅 도움 기능 있음
- 단순 알림 뿐만 아니라 음악 컨트롤러등의 미디어컨트롤러도 가능
- 안드로이드8.0부터 채널을 만들고 채널별로 알림 사용 중지 가능
- 채널 중요도 수준을 설정해야 함
- 채널에 속하는 알림들은 채널에 지정된 중요도를 갖게 됨
- 알림과 배너 : 긴급
- 알림음 울린다 : 높음(디폴트)
- 알림음 없다 : 중간
- 알림음 없고 상태 표시줄에 표시 x : 낮음
#### 알림 메시지(표시 메시지)
- FCM SDK에서 자동 처리
- 푸시를 눌러 앱을 들어와야만 뭔가 더 액션을 할 수 있음 -> 여러가지 케이스에 대응 어려움

#### 데이터 메시지
- 클라이언트 앱에서 처리
- 구현은 알림 메시지보다 어렵지만 앱이 백그라운드에 있든 포그라운드에 있든 앱에서 자체적으로 처리 -> 여러가지 케이스에 대응 가능
- 대부분의 경우 데이터 메시지로 구현
- 일반타입, 확장형 타입, 커스텀 타입
- 커스텀 타입은 잘 안 씀 
  - 다양한 기기와 다양한 해상도에서 작동하는지 파악하기 어려워서!
  - 일반적으로 축소된 거는 64dp 확장된 거는 256dp로 제한되는데 이 가이드 따라서 해도 잘릴 수 있음
  - 커스텀 타입은 ConstraintLayout 지원 안 함
  - 레이아웃은 RemoteViews사용해야 함
  - RemoteViews : 푸시 수신기가 아닌 다른 앱 (노티피케이션매니저(노티피케이션매니저도 별도의 앱이라 생각하면됨))에서 다룰 수 있는 클래스, 커스텀 노티피케이션, 위젯(홈 화면에서 보는 거)에서 자주 사용
  - 우리 앱에서 직접 관리하는 뷰는 아니다 → 이러이러한 스펙으로 렌더링해주세요라고 전달하면 그 앱에서 이걸 보고 만들어 준다. 일종의 설계도? 
  - 지원라이브러리의 스타일을 지정해서 그 디바이스 os에 대비되는 색상이 자동으로 설정되게 지정, 배경색은 함부로 건들지 않는다
 #### 알림의 탭 작업(터치)
- 모든 알림은 일반적으로 앱에서 상응하는 활동을 열려면 탭에 응답해야 한다.
- 이 작업을 하려면 PendingIntent 객체로 정의된 콘텐츠 인텐트를 지정하여 setContentIntent()에 전달해야 한다
- Intent.FLAG_ACTIVITY_SINGLE_TOP + onNewIntent Overriding으로 기존 액티비티 새로 생성 x 갱신 o
- 기존의 켜져있는 액티비티를 재활용해서 onCreate 대신 onNewIntent
- PendingIntent : 내가 직접 다루는 인텐트가 아니라 누군가에게(NotificationManager) 이 인텐트를 다룰 수 있는 권한을 줌
- 노티피케이션뷰에서 컨텐트인텐트 추가 -> 노티피케이션 매니저에 이 인텐트 전달 -> 노티피케이션 매니저가 인텐트를 수행해야할 때를 판단하여 수행하라고 전달
- 인텐트에 타입(일반, 확장, 커스텀)을 지정하지 않고 생성해서 전달하면 일반, 확장, 커스텀 번갈아가면서 알림을 줘도 결국 가장 마지막에 있는 타입으로 지정됨
  - 타입 구분해서 인텐트로 전달할 것
  - FLAG_UPDATE_CURRENT : 각각의 (일반, 확장, 커스텀)타입에 한해서는 각각 업데이트되게 함(타입별로 펜딩인텐트가 달라짐)
  
### Structure
![image](https://user-images.githubusercontent.com/66052467/151387928-d7189d3d-6154-48c9-b8d4-df2d27c30548.png)<br>

### Manifest
![image](https://user-images.githubusercontent.com/66052467/151392564-aa79e3a5-6b89-4a78-bdfe-e1d0f69a18e2.png)<br>

### Build.gradle
implementation platform('com.google.firebase:firebase-bom:29.0.4')<br>
implementation 'com.google.firebase:firebase-analytics-ktx'<br>
implementation 'com.google.firebase:firebase-messaging-ktx'<br>
![image](https://user-images.githubusercontent.com/66052467/151392676-9c704e20-b148-4d86-9930-f6a5c0f9d33e.png)<br>

### Console
![Untitled (2)](https://user-images.githubusercontent.com/66052467/151393758-803ee538-edaf-4728-a330-0255426586ca.png)<br>
