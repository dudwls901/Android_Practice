![Hnet com-image](https://user-images.githubusercontent.com/66052467/150384877-8452f3cc-119f-4f42-9693-8a3ba1a23c09.gif)
# Room을 이용한 메모앱

## Stack

- ConstraintLayout
- LayoutInflater
- RecyclerView

- Thread
- AsyncTask
- Room DB

## 기능
- roomDB crud
- 메모 삭제(리사이클러뷰 아이템 롱클릭)
- 메모 저장

---
- 레이아웃의 중첩은 퍼포먼스상 좋지 않기 때문에 지양 -> 유지보수, 성능 up
- LinearLayout으로 만들 수 있는 건 ConstraintLayout으로 다 만들 수 있음
- ConstraintLayout width, height 0dp로 화면 비율별로 반응형 크기

## Room DB란?
- Room
  - 안드로이드 로컬 DB SQLite를 더 쉽게 사용하게끔 해주는 라이브러리
  - SQLite는 보일러플레이트코드가 많다.
  - LiveData, RxJava를 위한 Observation으로 생성하여 동작 가능
- Room Component 3가지
  - Entity : DB안의 table
  - DAO : Data Access Object, 실질적으로 insert, delete 등 DB를 조작하는 여러 기능 정의
  - DataBase : DB 메인 진입점,  DAO를 반환하는 추상 메소드 포함, ENtity 리스트 포함
- SQLite vs Room vs SharedPreferences vs DataStore
  - 이들은 모두 데이터를 다루기 위한 기술로 앱이 꺼져도 데이터를 유지한다.<br>
    용도로 분류하면 (SQLite, Room) vs (SharedPreferences, DataStore)라 할 수 있다.<br>
    SQLite는 관계형 데이터베이스이고, 구조화된 데이터를 저장, 수정, 삭제할 때 사용한다.<br>
    또, 저장할 데이터의 양이 많을 때 사용하고, 데이터를 원하는 조건으로 조회할 때 사용한다.<br>
    ROOM은 SQLite를 보완한 데이터베이스의 데이터를 자바 or 코틀린 객체로 매핑해주는 <br>
    ORM(Object Relational Mapping) 라이브러리인데,<br>
    객체 지향 프로그래밍의 클래스와 관계형 데이터베이스의 테이블 모델의 불일치를 해결한다.
  
  - SharedPreferences와 DataStore는 key와 value의 쌍으로 이루어져있으며,<br>
    오직 1대1로만 매칭되며 공통된 key를 가지고 여러 데이터를 삽입할 수 없다.<br>
    즉, 같은 key를 가지고 데이터를 삽입하면 기존 데이터는 없어지게 된다. -> 1:1 관계만 성립<br>
    그에 반해 SQLite와 Room은 중복된 데이터가 허용되고 다수의 행에 데이터를 넣을 수 있다. -> 1:1, 1:n, n:m 관계가 성립<br>
    주로 자동 로그인이나 로그인 세션 유지 등의 간단한 데이터를 저장하는 데에 사용하고, SharedPreferences는 값을 xml기반의 파일 형태로 저장하고,<br>
    다른 앱과 데이터를 공유할 수 없다. SharedPreferences는 ANR의 위험이 있는 반면,<br>
    DataStore는 SharedPreferences를 보완한 Jetpack의 라이브러리로, 코루틴과 FLow를 통해 읽고 쓰기에 대한 비동기 API를 제공한다.
    
- Room 사용
  - dependencies 추가
  - kapt 플러그인 추가
  - 어노테이션 이용
  - Entity 생성(data class)
  - DAO 생성 (Interface)
  - DB 생성 (Abstract class, singleton)

### Structure
![image](https://user-images.githubusercontent.com/66052467/150393521-889d18ce-e231-43a5-8e90-b7b6dbc2cb14.png) <br>

### 메모 삭재 (LongClick)
![image](https://user-images.githubusercontent.com/66052467/150393691-a09f3d3a-71e6-44da-93b9-026d572e7d51.png)<br>
### 메모 삭재 (Interface)
![image](https://user-images.githubusercontent.com/66052467/150393740-6627eae3-3ad7-4525-b018-653bc1c5dba8.png)<br>

### Room(build.gradle) -> [최신 버전 확인](https://developer.android.com/jetpack/androidx/releases/room?hl=ko)
![image](https://user-images.githubusercontent.com/66052467/150394730-b93976d0-9658-47a8-a650-a2b509c30b26.png)<br>
![image](https://user-images.githubusercontent.com/66052467/150394732-683a01f6-d1e0-48f8-ade9-8cee7430f88d.png)<br>

### Room(model)
![image](https://user-images.githubusercontent.com/66052467/150393920-ffcf388b-de78-4da6-b719-372319fa0023.png)<br>
### Room(DAO)
![image](https://user-images.githubusercontent.com/66052467/150393975-c52dbb0c-3c8f-444f-8688-b642746d2ffc.png)<br>
### Room(DB Object)
![image](https://user-images.githubusercontent.com/66052467/150394054-0d530d21-40c3-471a-af61-560e4cbdf15c.png)<br>
### Room(insert)
![image](https://user-images.githubusercontent.com/66052467/150394388-1f6e282e-dfc1-4387-a07d-2e62872597d1.png)<br>
### Room(read)
![image](https://user-images.githubusercontent.com/66052467/150394428-25271b9a-6dc1-430d-8ce0-619687bf5b04.png)<br>
### Room(delete)
![image](https://user-images.githubusercontent.com/66052467/150394505-56f89f50-5719-4072-8a01-4156df8c9c7b.png)<br>

  
