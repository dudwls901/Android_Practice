# Android_Practice
Kotlin + Java / Android 공부 레포





### 📌다크모드 비활성화(테스트 편의를 위함)
onCreate()밑에
AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)<br>
onRequestPermissionResults과 같이 사용시 onRequestPermissionResults 빠르게 두 번 호출돼서 이슈 발생<br>
//앱 시작시 바로 권한 묻는 상황
