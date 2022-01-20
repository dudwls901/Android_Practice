<!-- ![calculator](https://user-images.githubusercontent.com/66052467/150379104-acea2346-d29a-4409-be5c-149a28445a45.gif) -->
![Hnet-image](https://user-images.githubusercontent.com/66052467/150379219-81d47e99-8daa-4498-8cc9-c3644b8f1924.gif)
# 계산기

## Stack

- TableLayout
- ConstraintLayout
- LayoutInflater

- Thread
- WorkerThread 사용
- runOnUiThread 사용(UIThread)
- SpannableStringBuilder

- Room 사용하기(로컬 DB)

- 확장함수
- data class

## 기능
- 계산기 기능(연산)
- roomDB crud
- 계산 기록 삭제
- 계산 기록 저장
- 버튼 ripple 효과
- 연산 예외처리
- 텍스트뷰 내 특정 텍스트 스타일 변경

---
- Handler
  - Thread간의 통신을 해줌
  - MainAcitivty에 있는 Handler는 MainThread(UIThread)와 연결되어있음
- android:onClick="함수이름"
  - xml에서 뷰바인드를 통해 onClick 함수 직접 연결
- Table Layout
  - 내부에 TableRow 사용
  - ShrinkColumns="\*" : 요소들 균일하게 사이징
- shape.xml <ripple> : 머티리얼 버튼 클릭시 동작하는 애니메이션 구현되어 있음
  - 커스텀 버튼(AppCompat Button)사용 시 직접 구현

### Structure
![image](https://user-images.githubusercontent.com/66052467/150381249-1251d4f9-2aec-4a70-a750-6def1e59c7ef.png) <br>

### SpannableStringBuilder
![image](https://user-images.githubusercontent.com/66052467/150381334-e792150a-197b-4501-8b96-8bfb2c22f182.png)<br>

### button ripple effect
![image](https://user-images.githubusercontent.com/66052467/150383616-d889f388-4bcd-4645-b27b-137111dcdd39.png)

### Room(model)
![image](https://user-images.githubusercontent.com/66052467/150381509-8c4f9936-d898-4b4b-94cf-23aa5bf52cf8.png)<br>
### Room(DAO)
![image](https://user-images.githubusercontent.com/66052467/150381529-f6a1ddd8-e6ce-4b70-9f4f-8cc6dda69320.png)<br>
### Room(DB Object)
![image](https://user-images.githubusercontent.com/66052467/150381534-d2f11f38-bc5a-44ba-b393-9175d118de0e.png)<br>
### Room(insert)
![image](https://user-images.githubusercontent.com/66052467/150382934-dc6e2d95-0d50-4baa-934e-9736aa0eaf82.png)
### Room(Read)
![image](https://user-images.githubusercontent.com/66052467/150382954-cefd9c89-72bc-4ac6-9a07-82aceba39cbb.png)

  
