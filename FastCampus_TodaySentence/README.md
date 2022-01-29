![Fastcampus_TodaySentence](https://user-images.githubusercontent.com/66052467/151644083-f044a6d6-ca7e-4b75-ad3c-15f46c1487de.gif)

# 오늘의 명언
## Stack

- Firebase Remote Config
  - 코드 수정 없이 명언 추가 가능
  - 코드 수정 없이 이름 숨기기 가능
  - Json 파싱
- ViewPager2(JetPack)
  - 무한 스와이프(페이징)
  - Progressbar(인디케이터) 

## 기능
- 좌우로 페이징하여 명언 띄우기

---
## 📌Firebase Remote Config(원격 구성)
- 앱을 업데이트하지 않아도 하루 활성자 수 제한없이 앱의 동작과 모양을 변경할 수 있는 툴
- Firebase Console에 remote config 값을 지정
  - 매개변수 최대 2000개, 조건 최대 500개, value 문자열 최대 80만 길이까지 가능 
- 서버에서 설정한 값을 getch하면 곧바로 값이 보이는 게 아님
- 매개변수값 가져오기, 캐싱 등의 중요한 작업은 클라이언트 라이브러리에서 처리, 새 값이 활성화돼서 UX에 영향을 주는 시점은 개발자가 제어
- 앱 업데이트는 Remote Config 사용 X -> 앱의 신뢰성을 해칠 수 있음
- 원격 구성 매개변수 키가 암호화 되어있지 않으니 매개변수에(Remote Config에) 기밀 데이터 저장하지 말 것
- 앱 스토어 올릴 때 리젝당한 경우 일단 검수 완료되게끔 작업해놓고 검수 후 다시 활성화 시키는 꼼수 금지

### Use Case
#### 1. 프로모션(가장 많이 사용)
- 이 타이밍에 어떤 문구나 이미지를 보여주고 싶을 때, 매번 이거 때문에 업데이트를 할 수 없으니<br>
  Remote Config를 통해 이미지, 안내문자, 홍보문자 변경 + 시간 설정 가능
#### 2. 비율 출시 메커니즘
- 새로운 기능 출시, 업데이트시 바로 100% 사용자에게 배포하기는 부담
- -> 조건 %달아서 10% 사용자에게 노출 -> 50% -> 100% 점진적으로 노출하여 실제로 동작하는지 파악 가능
#### 3. AB테스트
- 제한된 테스트 그룹에서의 새 기능테스트
- A랑 B그룹에 각각 독립적으로 다른 환경 제공해서 비교
<!--  ### ✅ Vector Assets  -->
<!--   - 다양한 해상도, 다중 밀도에 대응하기 위한 것 -->
#### 4. JSON을 사용해 여러가지 속성값들을 한 번에 수정

### 로딩 전략
- 서버에서 fetch 해오는 타이밍이랑 이 fetch해온 값을 실제로 액티베이트해서 앱에 보여주는 타이밍이 다르기 때문에 여러 전략 필요
- 사용자가 보고있는데 UI가 바뀌는 현상 지양
- Remote Config를 대량으로 요청하면 서버에서 블락할 수도 있으니 제한 사항 확인, 최소한의 요청 필요
- SDK 17.0.0 이하 버전에서는 60분동안 가져오기 요청 5회 제안(넘으면 FirebaseRemoteConfigFetchThrottledException)
- 이후 버전에선 늘어남
- -> 개발 단계에선 반복적으로 테스트하기 위해 임시로 가져오기 간격 최솟값을 설정 가능 (0으로 낮게 설정)
- 기본 값은 12시간(실제 배포하여 사용자들이 사용할 땐 기본값인 12시간으로 해야 한다)
- Remote Config 이슈 발생 시 Firebase Status Dashboard에서 Remote Config에 대한 상태가 정상인지 확인
- 
#### 1. 로드시 가져와서 활성화
- 앱 처음 시작할 때 FetchAndActivate로 불러온 다음에 Activate시켜서 값 업데이트
- UI 모양이 크게 변경되지 않는 구성에 적합
#### 2. 로딩 화면 뒤에서 활성화 (가장 무난)
- 로딩 인디케이터 돌려놓고 fetch 다 되면 보여주기
- 앱 시작시 스플래시 뜰 때 Remote Config fetch해오고 Activate시키는 전략
- fetch가 네트워크 상황에 따라 오래 걸릴 수 있기 때문에 ~초 이상 응답없으면 fetch하지 않고 그냥 넘어가기(TimeOut)
#### 3. 다음 시작시 새 값을 로드
- 앱 시작시 바로 fetch하지만 Activate는 하지 않음
- 내부적으로 가지고 있다가 그 다음 앱을 실행할 때 곧바로 Activate
- 변경되는 값을 fetch하고 기다리는 상황이 없기 때문에 바로 대기없이 반영 가능
- 좀 더 원활한 반영
- but 사용자가 최소 앱을 두 번 실행해야 반영되기 때문에 급하게 반영되어야 하는 데이터엔 사용 x
 
### Use Flow
1.0 파이어베이스 프로젝트 생성 <br>
1.1 ApplicationId 복사해서 패키지 네임에 넣기 <br>
1.2 디펜던시, 플러그인, google-services.json 삽입 등 설정 <br>
2. Remote Config Console에서 JSON데이터 설정 <br>
3. 퍼블리싱(게시) <br>
4. 액티비티에서 가져오기 간격 설정 후 FetchAndActivate호출 성공시 실제 키 값을 통해 데이터 받아오기 <br>
5. 받은 JSON 데이터를 파싱 <br>
6. 어댑터로 값 전달해서 화면에 띄우기 <br>

## 📌ViewPager2(JetPack)
- 화면 스와이프가 구현되어있는 뷰
- 기존 VIewPager에 비한 장점은?
  - Vertical Orientation Support : 기존엔 가로로만 스와이프, 이젠 세로로도 가능
  - RTL(Right to Left) 지원
  - NotifyDataSetChanged() : 기존 뷰페이저는 잘 동작이 안 됐으나 이 부분이 훨씬 개선됨
  - 기존의 뷰페이저와 다르게 리사이클러뷰 기반으로 작업되었음
  - 컬렉션을 보여주는 리사이클러뷰의 장점인 DiffUtil을 활용해 애니메이션을 원활히 작업 가능 
- material에 속해있어서 별도의 dependency 필요 없음
### PageTransformer 인터페이스
- 페이지 스크롤될 때 호출되는 인터페이스
- 커스텀하여 효과주기
- 뷰페이저에 set해서 사용
- Translation : 가로, 세로 어떻게 이동할지
- Scale : 얼마나 커지게 할지
- Alpha : 투명도(0~1)
- 크게 이 3가지를 조작하여 효과 구현
- setPageTransformer(@Nullabel PageTransformer)
  - PageTransformer.page : 변형을 적용할 페이지
  - PageTransformer.position : 단순히 0,1,2가 아니라 실제로 현재 보이고 있는 화면에서 상대적으로 어느 위치에 있는지 나타냄
  - ex) -0.5, 0, 1
  - 화면 정중앙이 0 우측은 + 좌측은 - 
### 무한 스크롤(페이징)
- 여러 가지 방법이 있는데 아래는 가장 무난한 방법
- ItemList의 크기를 Int.MAX_VALUE로 설정(getItemCount = Int.MAX_VALUE)
- 액티비티에서 어댑터로 값 넘길 때 리스트 사이즈의 중앙을 뷰페이저의 초기화면으로 설정
- 만약 실제 리스트의 첫 번째 값을 첫 화면에 띄우려면
- binding.viewPager.setCurrentItem(adapter.itemCount/2)-(adapter.itemCount / 2 % list.size)
- SmoothScroll을 false로 줘서 자연스럽게 초기 값을 중앙 인덱스로!
- 결과적으로 좌 우로 무한 스크롤이 가능하며, 사실 크기가 Int.MAX_VALUE이기 때문에 끝은 있음
### Use Flow
1. xml에 뷰페이저 선언
2. 렌더링할 item xml 생성
3. 데이터 모델 생성
4. 실제로 뷰페이저에 추가하기 위한 PagerAdapter생성(RecyclerView와 동일)
5. 액티비티에서 ViewPagerAdapter 연결



### ✅ Structure
![image](https://user-images.githubusercontent.com/66052467/151644301-ee49945c-952b-4154-a79b-3cc34196fc88.png) <br>

### ✅ MainActivity.initViews()
![image](https://user-images.githubusercontent.com/66052467/151650500-87bf3794-ee1d-4f95-8da7-f020c45311a0.png) <br>

### ✅ MainActivity.initData()
![image](https://user-images.githubusercontent.com/66052467/151650513-5d08e9a2-4590-484b-bc11-6f30e9be8cf1.png)<br>
### ✅ MainActivity.parseQuotesJson()
![image](https://user-images.githubusercontent.com/66052467/151650537-cefb6cbe-0e6b-4b06-94db-8124027a09bf.png)<br>
### ✅ MainActivity.displayQuotesPager()
![image](https://user-images.githubusercontent.com/66052467/151650539-147da723-dbd0-4885-a970-4af5aa72da6a.png)<br>

### ✅ QuotePagerAdapter.kt
![image](https://user-images.githubusercontent.com/66052467/151650329-f924ab73-0da3-4062-9b97-a5115491cab8.png) <br>

 
 
 
 
