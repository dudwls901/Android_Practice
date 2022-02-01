![Fastcampus_GoodsTrade](https://user-images.githubusercontent.com/66052467/151984216-4ab58e11-53d2-4e69-8857-21f2ccb722ba.gif)

# 중고 거래 앱

## Stack
- RecyclerView
- View Binding
- Fragment
- BottomNavigationView
- Floating Action Button
- Content Provider
- Firebase Storage (이미지 저장)
- Firebase Realtime Database
- Firebase Authentication
- Glide (이미지 로딩)

## 기능 + 구성
- Firebase Authentication 기능으로 로그인, 회원가입
- 회원 기반으로 중고 거래 아이템을 등록 가능
- Firebase Realtime Database로 사용자 정보 저장 및 다른 유저 정보 사용
- 아이템 등록시 사진 업로드를 위해 Firebase Storage 사용
- 회원 기반으로 채팅 화면 구현
- 액티비티에 바텀네비게이션뷰와 프래그먼트를 연결해 3개의 홈, 마이 페이지, 채팅방 화면 구현
- 리사이클러뷰로 각종 중고 거래 아이템, 채팅방, 메시지 등 구현


---
## Bottom Navigation View
- 내비게이션 역할을 해주는 하단에 버튼들로 구성
- 각각 버튼 클릭 시 위의 레이아웃의 프래금너트를 attach

## Fragment
- 앱의 UI에서 재사용 가능한 부분을 잘라서 독립적인 수명 주기를 가진 레이아웃
- 텍스트 뷰나 버튼처럼 액티비티 화면을 구성하는 뷰인데 그 자체만으로는 화면에 아무것도 출력되지 않는다.
  - Activity 내에 존재해야만 함
- 자체 입력 이벤트 처리 가능
- 액티비티에 작성할 수 있는 모든 코드는 프래그먼트에도 사용 가능하다.
- ex) 탭과 뷰페이저가 제공하는 한 화면을 하나의 프래그먼트로 개발
- ex 탭 버튼이 4개 있는 화면이 필요하면 액티비티에서 탭 버튼만 제공하여 사용자가 각 탭 버튼을 클릭하면 프래그먼트만 교체해서 출력
- 독립적인 수명 주기
- UI 모듈성 상승, 재사용 bb
- onCreate() X -> onViewCreated()
- 기본적으로 xml에 추가해서 사용하나 동적으로 객체 생성하여 화면에 출력할 수도 있음
- 액티비티의 생명주기 함수인 onCreate(), onStart(), onResume(), onPause(), onStop(), onDestroy() 그대로 가지고 있음
- 백스택을 이용하면 프래그먼트가 화면에 보이지 않는 순간 제거하지 않고 저장했다가 다시 이용할 수 있게 하는 기능 transaction.addToBackStack(null)

``` kotlin
//kotlin으로 Fragment 생성
class OneFragment : Fragment(){
	lateinit var binding: FragmentOneBinding
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View?{
			binding = FragmentOneBinding.inflate(inflater, container, false)
			return binding.root
	}
}

```
![Untitled (5)](https://user-images.githubusercontent.com/66052467/151987160-4c456558-525f-49f1-b547-35b21215554e.png)


## Project Flow
1. BottomNavigationView, FrameLayout(Fragment Container) 생성 
2. BottomNavigation에 menu item 연결
3. selector로 메뉴 효과 커스텀
4. BottomNavigation - Fragment 연결
5. Fragment xml 3 개 생성 및 kt 파일 생성
6. 프래그먼트 내부 리사이클러뷰 연결
7. 이전 틴더앱 Firebase 프로젝트에 현재 앱 추가
8. 파이어베이스 Storage, Realtime Database, Autentication 추가
9. 파이어베이스 Realtime Database에 ArticleModel(거래 물품 정보) 저장/불러오기
10. 플로팅 버튼(중고 거래 물품 생성) 생성 -> 아이디 없는 경우 회원가입
11. Content Provider로 External Storage 이미지 받아오기
12. Storage에 이미지 저장, 거래 물품 정보에 이미지 포함 업로드
13. Authentication으로 이메일 회원가입, 로그인 구현
14. 내가 누군가의 아이템 클릭하면 상대방과 나의 채팅방 생성
15. 채팅방 채팅 기능 구현

### Structure
![image](https://user-images.githubusercontent.com/66052467/151990479-93f38efe-3514-44a7-be67-dc286a54c968.png)<br>
![image](https://user-images.githubusercontent.com/66052467/151990513-5f0c24c2-8a32-4983-a381-98a7de06da46.png)<br>

### Manifest
![image](https://user-images.githubusercontent.com/66052467/151990868-78ac567e-aead-4691-937f-228a724a7a02.png)<br>

### Build.gradle
![image](https://user-images.githubusercontent.com/66052467/151990977-c493ada0-eb12-4620-819d-7353a1279761.png)<br>
![image](https://user-images.githubusercontent.com/66052467/151991025-2448b47e-3721-4288-8909-fb8ebe18139c.png)<br>
![image](https://user-images.githubusercontent.com/66052467/151991053-a612a780-4086-40e4-88dd-2c58ce1cdd55.png)<br>



### Firebase Console
![image](https://user-images.githubusercontent.com/66052467/151991399-1248a69f-d0e2-4d19-aaf2-554c33c36024.png)<br>
![image](https://user-images.githubusercontent.com/66052467/151991442-8e4f7134-d6b7-430f-983e-777454a6eba2.png)<br>
![image](https://user-images.githubusercontent.com/66052467/151991585-5879f107-6fe7-4f07-8fe7-6b4cc71398b6.png)



