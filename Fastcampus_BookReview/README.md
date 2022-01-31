![Hnet-image (2)](https://user-images.githubusercontent.com/66052467/151707258-1e97e7c2-0f18-4147-adaa-9462c68dfd98.gif)
# 인터파크 도서 리뷰 앱

## Stack
- RecyclerView
- View Binding
- Retrofit (API 호출)
- Glide (이미지 로딩)
- Room (로컬 db)
- Open API 사용

## 기능
- 인터파크 Open API를 통해 베스트셀러 정보를 가져와서 화면에 그림
- 인터파크 Open API를 통해 검색어에 해당하는 책 목록을 가져와서 화면에 그림
- 최근 검색어 저장, 최근 검색어로 검색하기
- Room을 이용하여 검색 기록을 저장하고 삭제 가능
- Room을 이용하여 개인 리뷰를 저장

---
## Open API?
- 개발자에게 사유 응용 소프트웨어나 웹 서비스의 프로그래밍적인 권한을 제공
- 누구나 사용할 수 있게 Open된 API
- 인증키가 있어야 사용 가능 (노출될 시 삭제 후 재발급)
- 현재 프로젝트에선 API key를 전역으로 쉽게 사용하기 위해 xml-values에 저장하여 사용
- 액티비티에서 getString(R.string.~key)로 사용
- API key는 자동으로 gitignore에 추가되는 Local-properties에 넣고 사용(보안)
- 팀 프로젝트시 따로 관리
## Glide?
- 서버에서 받아온 이미지 URL을 띄우기 위한 라이브러리

## Retrofit2?
- 안드로이드에서 가장 유명하고 널리 사용되는 Type-Safe REST 클라이언트 중 하나
- 내부적으로 HTTP 및 네트워크 호출에 OkHTTP를 사용
- REST 클라이언트이기 때문에 안드로이드의 다른 네트워킹 라이브러리와는 다르다
- 대부분의 네트워킹 라이브러리 Volley, OkHTTP, 기타는 동기식/비동기식 요청, 우선순위 지정, 요청 정렬, 동시/병렬 요청, 캐싱 등에 중점을 둔다.
- 레트로핏은 네트워크를 호출하고 데이터를 파싱하는 작업을 메서드 호출과 같이 만들어주는 데 더 중점을 두는데, HTTP API를 자바 인터페이스로 변경해준다.
- 네트워크와 관련된 문제는 자체적으로 해결하지 않고 내부적으로는 OkHTTP에 위임한다.
- java 8 이상, Android API 21 이상
- gson으로 바꾸어주는 컨버터 라이브러리 내장(retrofit2-converter-gson 라이브러리 추가)
  - gson : 직렬화해서 데이터를 표현하는 방식인 json을 바로 Object로 사용할 수 있게 도와주는 구글에서 만든 방식
- DTO : Data Transfer Object
  - 계층간 데이터 교환을 하기 위해 사용하는 객체
  - 로직을 갖고 있지 않은 순수한 데이터 객체
  - getter/setter만 가진다.
  - baseUrl은 https 사용 

## RecyclerView
- 화면에 보이는 리스트들에서 위 아래로 한 두개 뷰까짐나 미리 그려놓고 스크롤 움직이면 미리 그려진 뷰에 데이터만 갱신해서 새 값을 보여줌
- 뷰를 재활용
- 레이아웃 매니저와 adapter 필요

## Project Flow
1. API 신청
2. 레트로핏2 설정(retrofit2, converter-gson, internet permission)
3. model - DTO 만들기
4. api - serviceInterface에 API호출 구현
5. Activity에서 ServiceInterface Retrofit으로 생성
6. service 객체로 함수 호출 및 enqueue Override
7. Glide 라이브러리 추가 및 서버에서 받아온 이미지 url을 adpater에서 사용하여 바인딩
8. Room 그레이들 설정
9. Room 데이터 모델 생성(Entity)
10. AppDatabase 추상 클래스로 db 생성
11. DAO 생성
12. 액티비티에서 AppDatabase 싱글턴 객체 생성
13. DAO 사용
14. 상세 페이지 putExtra로 DTO 직렬화하여 한 번에 넘기기 위해 plugin id 'kotlin-parcelize'사용
15. db에 새로운 Entity 넣었으니 DB 버전 업 -> Migraion 구현
  
### Structure
![image](https://user-images.githubusercontent.com/66052467/151729535-455db42c-3843-44fc-80be-12a0e529bd0a.png)<br>
![image](https://user-images.githubusercontent.com/66052467/151729536-c1429896-ec81-4976-9372-285e50b663e1.png)<br>

### Manifest
![image](https://user-images.githubusercontent.com/66052467/151729569-8c75178b-f50d-482e-b7a4-1afa4dd22e72.png)<br>

### Build.gradle
![image](https://user-images.githubusercontent.com/66052467/151729597-5c716834-a464-47dc-a8e6-8ed4c8fc7965.png)<br>
![image](https://user-images.githubusercontent.com/66052467/151729624-c19778df-a203-45db-bec4-75b6081a0724.png)<br>

### RecyclerView view Binding
![image](https://user-images.githubusercontent.com/66052467/151729838-6f181c63-77c7-44c7-8807-019eb4a8c114.png)<br>

### 사용 API
[인터파크 Book Open API](http://book.interpark.com/bookPark/html/bookpinion/api_main.html)<br>
