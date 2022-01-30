package fastcampus.part3.fastcampus_alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver : BroadcastReceiver() {

    companion object{
        const val NOTIFICATION_ID = 1000
        const val NOTIFICATION_CAHNNEL_ID = "1000"
    }

    //브로드캐스트리시버에 실제로 pendinIntent가 수신이 되었을 때 호출이 되는 콜백 함수
    override fun onReceive(context: Context, intent: Intent) {

        //api 26 미만에서는 채널 필요 없지만 26 이상에서는 필요
        createNotificationChannel(context)
        notifyNotification(context)
    }
    //메인액티비티는 컨텍스트 대신 this사용 가능했음
    //액티비티 자체는 바로 context라고 해도 무방했음
    //브로드캐스트 리시버는 액티비티가 아니기 때문에 context를 따로 받아와야 함
    //context : 지금 실행하고 있는 앱의 상태나 맥락 등을 담고 있음 ,
    // 안드로이드 앱이 환경에서 글로벌 정보나 api나 시스템이 관리하고 있는 정보(sharedPreferences, 리소스 파일 접근)등에 접근할 때 필요한 정보
    // 액티비티 자체에는 지금 실행하고 있는 환경 자체가 sharedPreferences, 리소스 파일 등 접근에 용이함, context를 상속받고 있음
    // 브로드캐스트 리시버는 백그라운드 브로드캐스트에서 새로운 pendingIntent가 넘어와서 실행되는 기능이기 때문에 context를 받아와야 함

    private fun createNotificationChannel(context: Context){
        //26 버전 오레오
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CAHNNEL_ID,
                "기상 알람",
                NotificationManager.IMPORTANCE_HIGH
                //무음일 때는 진동이 안 되게 한다든지, 진동모드일 때는 소리가 나지 않게 한다든지가
                // important priority에 따라 다르게 동작
                //알람은 중요한 notify이기 때문에 important high
            )
            //make channel
            NotificationManagerCompat.from(context).createNotificationChannel(notificationChannel)
        }
    }
    private fun notifyNotification(context: Context){
        with(NotificationManagerCompat.from(context)){
            val builder = NotificationCompat.Builder(context, NOTIFICATION_CAHNNEL_ID)
                .setContentTitle("알람")
                .setContentText("일어날 시간입니다.")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                //위까지 필수값
                //7.0버전 이하에는 노티피케이션마다 중요도를 별도로 지정
                .setPriority(NotificationCompat.PRIORITY_HIGH)
            notify(NOTIFICATION_ID, builder.build() )
        }
    }
}