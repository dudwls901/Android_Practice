![Hnet-image (1)](https://user-images.githubusercontent.com/66052467/151829845-40562247-17e1-409d-9f18-0f03d12b8b8d.gif)

# 틴더앱

## Stack
- Firebase Authentication
  - Email Login
  - Facebook Login
- yuyakaido / CardStackView
- Firebase RealtimeDatabase 사용 
## 기능
- Firebase Authentication을 통해 이메일 로그인과 페이스북 로그인 가능
- Firebase RealTime Database를 통해 기록을 저장하고 불러올 수 있음
- Github에서 Open Source Library를 찾아 사용 가능
- 스와이프로 like, disLike
- 커플매칭

---
## CardStackView
- Open Source Library
- https://github.com/yuyakaido/CardStackView
- 카드처럼 뷰를 쌓고, 스와이프하여 뷰를 없애는 애니메이션을 사용할 수 있음
- 어댑터는 리사이클러뷰의 어댑터를 사용

## Firebase Authentication
- SNS 로그인, 이메일 인증 등의 통합환경 제공
- 이메일 로그인
  - 비밀번호 없게도 가능 -> 이메일 + 인증 번호로 바로 로그인
- SNS 로그인 
  - Facebook 로그인 버튼 누르면 facebook 앱/웹 열리고 로그인 완료되면 다시 액티비티 콜백으로 넘어온다.
  - onActivityResult 값을 Facebook SDK에 전달 -> accesToken 반환

## Firebase Realtime Database
- 데이터 변동 이벤트를 리스너로 내려줘서 실시간으로 알려줌

## Project Flow
1. 파이어베이스 설정
2. Authentication 시작
3. Realtime Database 시작 (미국)
4. Storage 포함된 google-services.json 재설정
5. Authentication 콘솔 설정(이메일, 비밀번호, sns 등)
6. 로그인 액티비티, xml 생성 및 Firebase Auth 객체 생성
7. 로그인 액티비티에서 로그인, 회원가입 처리
8. 메인 액티비티에서 onStart()에 로그인 안 된 경우 로그인 액티비티 실행
9. Facebook for Developer에서 앱 생성
10. Facebook login 종속성 추가
11. facebook login button.xml 생성 및 콜백 매니저 객체 생성
12. RegisterCallBack으로 onActivityResult로 받아온 데이터의 결과로 accessToken을 파이어베이스 auth에 전달하여 로그인 확인 
13. Realtime Database 설정
14. userDB 객체 생성하고, 이름 없으면 다이얼로그 띄워서 이름 입력 후 UserId, userName으로 DB 갱신
15. 오픈소스 라이브러리 CardStackView 설정
16. CardStackview 사용법에 따라 어댑터, item.xml, 레이아웃매니저 연결
17. 우측 스와이프로 다른 유저 like, 좌측 스와이프로 다른 유저 disLike 구현 및 상대 DB에 like값과 id 저장

### Structure
![image](https://user-images.githubusercontent.com/66052467/151907201-17dbfc55-1c2c-40e0-83d0-6a89604f181f.png)<br>

### Manifest
![image](https://user-images.githubusercontent.com/66052467/151907259-1ebe7ff3-46a3-43b1-b660-62e612d82ae9.png)<br>
![image](https://user-images.githubusercontent.com/66052467/151907258-9ce0a2eb-963b-4d7f-bed2-b7564ac5292d.png)<br>

### Build.gradle
![image](https://user-images.githubusercontent.com/66052467/151907301-6d8acea4-c4e3-4091-89cf-a92759034d31.png)<br>

### Firebase Authentication
![image](https://user-images.githubusercontent.com/66052467/151907349-70960523-4d43-4470-b195-1e6b920982f6.png)<br>

### Firebase Realtime Database
![image](https://user-images.githubusercontent.com/66052467/151907388-9f1707ff-00e6-4463-93f0-49aea8b90de1.png)<br>

