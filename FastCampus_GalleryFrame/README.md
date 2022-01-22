![Fastcampus_GalleryFrame](https://user-images.githubusercontent.com/66052467/150645604-f52c4eb4-cf1d-46b2-b0e7-e516f9a74741.gif)
# 전자액자(자동재생 액자)
## Stack

- LinearLayout
- ImageView(중첩)

- UIThread
- Animation
- Timer
- Content Provider
  - SAF(Storage Access Framework) - 갤러리 저장소
- Android Permission 
- Life Cycle


## 기능
- 사진 추가 기능(로컬 갤러리 접근)
- 추가한 사진 액자로 띄우고 자동 넘김
- 저장소 접근 권한 팝업(권한에 대한 팝업 + 권한 얻는 팝업)


---
- app:layout_constraintDimensionRatio="H,3:1" 
  - 가로 3 세로 1 비율
- SAF(Storage Access Framework)
  - https://developer.android.com/guide/topics/providers/document-provider?hl=ko
  - Document Provider : 문서(파일)을 제공하는 주체
  - Client app: 프로바이더가 제공하는 문서를 사용하는 앱
  - Selector: 일종의 시스템 UI로, 클라이언트 앱에서 필요한 파일을 사용자가 선택할 때 사용됩니다
  - 디바이스에는 여러 프로바이더가 존재할 수 있다.
  - 사용자는 프로바이더들이 제공하는 모든 파일을 탐색할 수 있다.
  - 앱은 프로바이더가 제공하는 문서에 대한 접근 권한을 가질 수 있다.
  - 이 접근 권한으로 앱은 파일을 추가, 편집, 저장 및 삭제할 수 있다.
  - USB가 연결되었을 때 USB의 데이터를 제공하는 프로바이더도 있다.  

### Structure
![image](https://user-images.githubusercontent.com/66052467/150645585-cd60caf4-25b8-4e33-aa58-c2af6535998a.png) <br>

### 1.사진 추가하기
#### 1)initAddPhotoButton
![image](https://user-images.githubusercontent.com/66052467/150645675-ef106a36-0dc5-4b50-9ac3-a22699f71fdd.png)<br>
#### 2)showPermissionContextPopup
![image](https://user-images.githubusercontent.com/66052467/150645765-32120b02-4cb0-4919-b57a-ba935bcf1727.png)<br>
#### 3)onRequestPermissionResult
![image](https://user-images.githubusercontent.com/66052467/150645807-20187adf-4022-4569-98f7-0c82faf97f01.png)<br>
#### 4)navigatePhotos
![image](https://user-images.githubusercontent.com/66052467/150645824-965b370d-0e0b-4bfb-9951-9e99a4480cf8.png)<br>

#### 5)onActivityResult
![image](https://user-images.githubusercontent.com/66052467/150645829-ec551516-759d-47da-ae82-22cb471cb4a2.png)<br>

### initStartPhotoFrameModeButton
![image](https://user-images.githubusercontent.com/66052467/150645880-d86bf62b-69af-4c99-8d2b-6632787abe0d.png)<br>

### 액자 띄우고 자동 넘김
#### 1)getPhotoUriFromIntent
![image](https://user-images.githubusercontent.com/66052467/150645924-796071b5-900b-49e1-b5ed-cdddddfd8156.png)<br>
#### 2)startTimer
![image](https://user-images.githubusercontent.com/66052467/150645967-d26cfba2-19cd-4ff1-84fb-9f356d74acfb.png)<br>
#### 3)user LifeCycle
![image](https://user-images.githubusercontent.com/66052467/150646040-43adaa48-7d80-494b-8a32-712999c0c295.png)<br>

