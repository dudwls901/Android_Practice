![Hnet-image (1)](https://user-images.githubusercontent.com/66052467/151690850-b7ce193a-688c-4946-a662-5689f4422110.gif)

# 알람앱
## Stack

- AlarmManager
- Notification
- Broadcast Receiver
- SharedPreferences
## 기능
- 지정된 시간에 알람이 울리게 한다 (그냥 푸시만)
- 지정된 시간 이후에는 매일 같은 시간 반복 알람
---
0. 뷰 초기화
1. 알람 설정
2. 알람 데이터 가져오기, 저장하기 (SharedPreferences)
3. 가져온 데이터 뷰에 그리기

AlarmManager를 통해 휴대폰으로 정시알람 할 수도 있고,<br>
서버에서 노티피케이션 푸시를 정시에 주어서 그때 잠깐 서비스 활성화, 데이터 동기화하여 알람도 가능<br>


### [Notification?](https://github.com/dudwls901/Android_Practice/tree/main/FastCampus_FireBasePractice)
### BroadCast Receiver?
- 기기가 켜져있는지, 배터리가 얼마나 있는지, 와이파이가 켜져있는지 등의 시스템에서 일어나는 작업들을 catch
- 앱에서 BraodCastReceiver를 등록해서 이런 시스템 방송을 수신해서 사용
- 시스템 뿐만 아니라 다른 앱과의 상호작용 가능

### doze mode?
- Android 6.0 (API 23 LEVEL) OREO부터 생긴 기능
- 사용자가 휴대폰을 충전시켜놓지 않고 장기간 휴대폰을 사용하지 않을 시 안드로이드 os는 잠자기(doze)모드 돌입
- 전화같이 긴급한 상황이 아니고선, 아무런 background 작업도 할 수 없다
- doze 모드에서도 실행되어야 하는 Alarm을 설정하기
  - alarmManager.setAndAllowWhileIdle()
  - alarmManager.setExactAndAllowWhileIdle()
  - 잠자기 모드여도 설정한 시간에 휴대폰이 깨어나서 알람 실행
  - 사용할 때 왜 써야 하는지 충분한 설명이 필요 
  
### Structure
![image](https://user-images.githubusercontent.com/66052467/151691134-ad803b65-de4a-4ecb-8dc0-16ead7180f8b.png)<br>

### Manifest
![image](https://user-images.githubusercontent.com/66052467/151691185-4ff31784-b5d5-40ac-bf7b-5ff0f644e285.png)<br>

