package fastcampus.part3.fastcampus_alarm

data class AlarmDisplayModel(
    //데이터 클래스의 프로퍼티는 기본적으로 상수 선언이 좋은데
    //changeTime할 때는 알람 디스플레이 모델을 새로 만들지만 onOff만 바꿀 때는 onoff만 바꿀 거기 때문에 변수(var)로 선언
    val hour: Int,
    val minute: Int,
    var onOff: Boolean
) {
    val timeText: String
        get() {
            val h = "%02d".format(if (hour < 12) hour else hour - 12)
            val m = "%02d".format(minute)
            return "$h:$m"
        }
    val ampmText: String
        get() {
            return if (hour < 12) "AM" else "PM"
        }
    val onOffText: String
        get(){
            return if(onOff) "알람 끄기" else "알람 켜기"
        }


    fun makeDataForDB(): String {
        return "$hour:$minute"
    }


}
