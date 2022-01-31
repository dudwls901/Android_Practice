# Kotlin + Java / Android 공부 레포


## 📗Studied
[✅Room](https://github.com/dudwls901/Android_Practice/tree/main/Room) <br><br>
[✅빌더 패턴](https://github.com/dudwls901/Android_Practice/blob/main/BuilderPattern.md)<br><br>
[✅View Binding](https://github.com/dudwls901/Android_Practice/tree/main/ViewBinding)<br><br>
[✅컴포즈 찍먹](https://github.com/dudwls901/Android_Practice/tree/main/ComposePractice)<br><br>
[✅FCM(Firebase)](https://github.com/dudwls901/Android_Practice/tree/main/FastCampus_FireBasePractice)<br><br> 
[✅Remote Config(Firebase)](https://github.com/dudwls901/Android_Practice/tree/main/FastCampus_TodaySentence)<br><br> 
[✅ViewPager2, 무한 스크롤](https://github.com/dudwls901/Android_Practice/tree/main/FastCampus_TodaySentence)<br><br> 
<!-- 
[✅]()<br><br> 
-->
<!-- 
<details>
<summary>📌</summary>

<pre>
</pre>

</details>
-->
<!-- 
&nbsp; 
-->

## 🤷‍♀️Small Tips
알게 된 작은 팁과 더 공부할 내용들을 모았습니다.<br>
사소한 내용도 숙지한다면 차후 주니어 개발자에서 시니어 개발자로 성장하기 위한 좋은 발판이 될 것입니다.<br>
<details markdown="1">
<summary>📌다크모드 비활성화(테스트 편의를 위함) </summary>
<br>
<pre>
onCreate()밑에
AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)<br>
onRequestPermissionResults과 같이 사용시 onRequestPermissionResults 빠르게 두 번 호출돼서 이슈 발생<br>
//앱 시작시 바로 권한 묻는 상황
</pre>
</details>

<details>
<summary>📌코틀린 리플렉션(::) -> 내용 추가 예정</summary>

리플렉션(::)?
- 코드를 작성하는 시점에는 런타임상 컴파일된 바이트코드에서 내가 작성한 코드가 어디에 위치해 있는지 알 수 없기 때문에
  바이트코드를 이용해 내가 참조하려는 값을 찾기 위해 사용<br>
- Kotlin
  - SomeClass::class // 클래스 자체를 리플렉션
  - SomeInstance::class //인스턴스에서 클래스를 리플렉션<br>
- Java
  - SomeClass.class // 클래스 자체를 리플렉션
  - SomeInstance.getClass() // 인스턴스에서 클래스를 리플렉션
- 클래스 타입의 레퍼런스를 등록할 때 '.java'를 추가해야 하는 경우는 자바로 작성된 API를 코틀린에서 이용할 때임
- 즉 코틀린에서 자바 API를 이용할 때는 DetailActivity::class.java로 작성
- 함수 참조도 가능 println(numbers.filter(::isOdd))

</details>

<details>
<summary>📌사용자가 겪은 버그 대응 방법(크래시)</summary>

Q. 앱 개발 단계에선 이슈 발생 시에 에러 메시지등을 통해 확인하고 대응할 수 있는데<br>
&nbsp; &nbsp; &nbsp; 배포 후 사용자가 사용하다가 발생하는 버그도 개발자가 받아볼 수 있나요?<br>
&nbsp; &nbsp; &nbsp; 앱 런칭 경험이 없어서 어떻게 파악해서 대응하는지 궁금합니다.<br>

- Firebase에 크래시리틱스로 쉽게 구현 가능
- 애초에 플레이스토어 콘솔에서 제공해줌
- 크래시를 디텍팅하는 방법 자체가 달라서 구글 콘솔에는 뜨지만 파베크래시에는 안뜨는 크래시도 있어서 같이 봐야 함
</details>

<details>
<summary>📌마켓에 앱 ‘업데이트’가 아니라 ‘열기’로 보이는 이슈</summary>

마켓에는 1.1버전이 올라가 있는데, 1.0버전 사용하는 사람이

마켓에 들어가면 ‘업데이트’가 아니라 ‘열기’로 보이면서 1.1버전을 다운로드 받지 못하게 되는 이슈

마켓에서 자주 겪는 상황, 배포되는 데에 시간이 걸려서 발생하는 문제 or 캐시 문제

대응- > 버전 안 맞으면 앱에서 다이얼로그 띄우기 or 수동 업데이트 기능 or 홈페이지를 통한 배포 기능

사용자 대응 →고객응대하는 분 왈 : 구글 스토어 캐시 삭제하면 해결 or 메뉴 버튼 눌러서 앱 resume시키면 업데이트 뜸

앱에서 외부를 통해 업데이트할 수 있는 안내문구나 링크 ui있는 거 구글한테 걸리면 앱 삭제당함!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

홈페이지를 통한 배포 문제점

1. 보안 취약
2. 설치 시 기존 설치되어있는 앱이랑 충돌 일어나는 경우가 생기면 기존 거 삭제해야하는 불편함(이 있을 것으로 예상)
3. 유저한테서 '알 수 없는 소스에서 받은 APK 설치' 권한을 허용받아야만한다는 점
4. 앱 접근성 → 엄청 유명한 앱 아닌 이상 홈페이지까지 찾아가서 설치하지 않을듯
5. 사칭한 유사 앱으로 인한 피해 발생 우려
6. Play App Signing 쓸 경우 앱서명 코드가 바뀔 수 있으니 외부 서비스 못 쓸 수도 있음
![Untitled (3)](https://user-images.githubusercontent.com/66052467/151651782-491e2f1e-7fdb-4b2b-985a-75cee0138660.png)

</details>

<details>
<summary>📌JSON?</summary>

JSON : JavaScript Object Notation
- 경량화된 데이터 교환 방식
- 많은 경우 json 형식으로 데이터 주고 받음
- json의 일반적인 구조는 이름과 값 (key, value)
- 배열로도 표현 가능
- json 데이터를 object
- +gson
</details>

<details>
<summary>📌sp vs dp</summary>

sp : 텍스트 사이즈에 주로 사용, 휴대폰 설정에서 텍스트 크기 조절하면 확대 or 축소됨
dp : 뷰들에 주로 사용, 고정
</details>

<details>
<summary>📌Android?</summary>

- Android Framework = Java API Framework<br>
(API LEVEL) <br>
- Android = Android Flatform<br>
(Android Version)<br>
- Android OS = Android System : Android를 휴대할 수 있는 디바이스 기기의 운영체제로 사용한다<br>

</details>

<details>
<summary>📌서버db vs 로컬db 어떤 데이터를 저장해야 할까?</summary>
데이터 스토어/sharedpreferences - 자동 로그인?

- 로그인 토큰은 안드로이드 마스터키로 박아둬도 됨(키스토어)

룸 (로컬 디비)-?

서버 -?

1.필수적으로 서버에 저장해야 하는 경우

2.노하우의 영역

서버에서 저장할 데이터, 앱에서 저장할 데이터 구분한 후에는 

앱에서 저장할 데이터를 저장할 때 room vs datastore 정하기

서버 vs 로컬db

1. 서버에 저장해야 하는 근거
- 해당 데이터 관련 처리를 앱에서 할 수 없는가?
    - 기존 서버와 앱에서 저장되는 데이터들을 기반으로 새로 추가되는 데이터의 용도와 그에 대한 처리를 고려했을 때, 앱에서는 도저히 처리가 불가능한지
    - ex) 다른 유저한테도 보여져야 할 데이터들 프로필 등
- 해당 데이터 관련 처리를 앱에서 하면 안 되는가?
    - 앱에서 데이터를 위/변조하거나, 처리 도중 앱이 종료되는 등의 상황을 고려했을 때 피해가 상당할 우려가 있는지
    - ex) 결제 내역

노하우의 영역

1. 서버
- 앱 운영 측에서 제공하는 고정 데이터

2. 로컬

3. 서버 + 로컬
- 서버에 메인으로 저장하는 데이터라도 캐싱을 목적으로 로컬에 저장하는 경우
    - 로컬을 임시 저장처럼 사용(성능을 위해)
    - UI에 미리 띄우는 용도로만 사용, ~~등
    - EX) 각 계좌 별 현재 잔고
- 로컬에서 계산하고 처리하여 임시로 냅뒀다가 앱 종료라든지 어떤 동작을 할 때 한 번에 서버로 날리는 거(임시 데이터가 손상되어도 큰 문제가 없는 경우 가능)
- 주기적으로 동기화되는 클라우드 내 파일 내용

텍스트 편집 자동 저장은 상황에 따라 서버 or 로컬
자동 저장은 서비스 성격에 따라 해야되냐 말아야되냐고, 해야되면 효율화 시키는 방법은 많다.
  
2명이상이 동시에 같은 화면 보면서 편집하는 서비스면 무조건 서버를 통한 동기화가 필요하고, 서버의 disk io를 줄이기 위한 방법은 서버 개발자들이 효율화 시킴
</details>


&nbsp;&nbsp;&nbsp;&nbsp;📌Any : 모든 타입 가능, Unit: 반환문이 없는 함수, Nothing : null이나 예외를 반환하는 함수<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;📌리사이클러뷰이기 때문에 재사용할 때 visibility 제대로 처리 안 하면 안 보일 때도 있음<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;📌key 등 보안 중요 파일은 .gitignore를 이용해 제외하고 올리자. 협업시엔 따로 파일 관리 및 공유<br><br>
<details markdown="1">
<summary>📌Plugin 추가는 apply plugin이 아닌 id ~로 추가 </summary>
<br>
<pre>
plugins {
    id 'com.android.application'
    id 'kotlin-android'
    id 'com.google.gms.google-services'
}
</pre>
</details>




<details markdown="1">
<summary>🟩 xml skill </summary>

🖐 텍스트 ... 처리
<pre>
android:maxLines="6"
android:ellipSize="end"
</pre>

🖐 windowBackground 설정<br>
thmes.xml
```
<item name="android:windowBackground">@color/pomodoro_red</item>
```

🖐 커스텀 버튼<br>
Shape.xml <ripple></ripple> : 이걸 통해 AppCompatButton(머티리얼 적용 안 되어있는 버튼 커스텀)

🖐 StatusBar 색상 변경 및 아이콘 색상 변경<br>
thems.xml
```
<item name="android:statusBarColor">@color/white</item>
<item name="android:windowLightStatusBar">true</item>
```

</details>
