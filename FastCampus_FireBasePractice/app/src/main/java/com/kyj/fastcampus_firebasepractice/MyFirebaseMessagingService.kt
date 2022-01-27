package com.kyj.fastcampus_firebasepractice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    //토큰은 자주 변경이 될 수 있음
    //새 기기에서 앱 복원
    //사용자가 앱 삭제/재설치
    //사용자가 앱 데이터 소거
    //실제 라이브서비스를 이용할 때는 onNewToken 오버라이드해서 토큰이 갱신될 때마다 서버에 해당 토큰 갱신해줘야 함!!!!
    //현재 프로젝트는 라이프사이클도 짧고 굳이 onNewToken에 대한 처리는 따로 x
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    //실제로 fcm에서 메시지 수신할 때마다 onMessageReceived 호출
    //매니페스트에 등록해야 함
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        createNotificationChannel()

        //[], () 다 가능 해쉬 형태
        val type = message.data["type"]
            ?.let {
                //enumClass.valueOf == enum이름과 동일한 값을 동일한 값을 전달했을 때 그에 상응하는 enum 값을 반환
                NotificationType.valueOf(it)
            }
        val titleRecieved = message.data.get("title")
        val messageReceived = message.data.get("message")

        type ?: return


        //만든 노티피케이션을 노티파이(알림 띄우기)
        NotificationManagerCompat.from(this)
            .notify(type.id, createNotification(type, titleRecieved, messageReceived))

    }

    private fun createNotification(
        type: NotificationType,
        title: String?,
        message: String?
    ): Notification {
        //intent 만들어서 pendingIntent로 -> 내가 아니라 노티피케이션 매니저가 인텐트를 다룰 수 있게 한다.
        val intent = Intent(this, MainActivity::class.java).apply{
            putExtra("notificationType", "${type.title} 타입")
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(this, type.id, intent, FLAG_UPDATE_CURRENT)

        //노티피케이션 뷰 조립하기
        //빌더의 속성을 통해 다양한 타입의 노티피케이션 구현
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle(title)
            .setContentText(message)
            //7.0버전 이하에는 노티피케이션마다 중요도를 별도로 지정
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
                //클릭 시 노티피케이션 자동으로 닫히게
            .setAutoCancel(true)


        when (type) {
            NotificationType.NORMAL -> Unit
            NotificationType.EXPANDABLE -> {
                notificationBuilder.setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(
                            "\uD83D\uDE00 \uD83D\uDE03 \uD83D\uDE04 \uD83D\uDE01 \uD83D\uDE06 \uD83D\uDE05 \uD83D\uDE02 \uD83E\uDD23 " +
                                    "\uD83E\uDD72 ☺️ \uD83D\uDE0A \uD83D\uDE07 \uD83D\uDE42 \uD83D\uDE43 \uD83D\uDE09 \uD83D\uDE0C " +
                                    "\uD83D\uDE0D \uD83E\uDD70 \uD83D\uDE18 \uD83D\uDE17 \uD83D\uDE19 \uD83D\uDE1A \uD83D\uDE0B " +
                                    "\uD83D\uDE1B \uD83D\uDE1D \uD83D\uDE1C \uD83E\uDD2A \uD83E\uDD28 \uD83E\uDDD0 \uD83E\uDD13 " +
                                    "\uD83D\uDE0E \uD83E\uDD78 \uD83E\uDD29 \uD83E\uDD73 \uD83D\uDE0F \uD83D\uDE12 \uD83D\uDE1E " +
                                    "\uD83D\uDE14 \uD83D\uDE1F \uD83D\uDE15 \uD83D\uDE41 ☹️ " +
                                    "\uD83D\uDE23 \uD83D\uDE16 \uD83D\uDE2B \uD83D\uDE29 " +
                                    "\uD83E\uDD7A \uD83D\uDE22 \uD83D\uDE2D \uD83D\uDE24 \uD83D\uDE20 \uD83D\uDE21 \uD83E\uDD2C " +
                                    "\uD83E\uDD7A \uD83D\uDE22 \uD83D\uDE2D \uD83D\uDE24 \uD83D\uDE20 \uD83D\uDE21 \uD83E\uDD2C " +
                                    "\uD83E\uDD7A \uD83D\uDE22 \uD83D\uDE2D \uD83D\uDE24 \uD83D\uDE20 \uD83D\uDE21 \uD83E\uDD2C " +
                                    "\uD83E\uDD7A \uD83D\uDE22 \uD83D\uDE2D \uD83D\uDE24 \uD83D\uDE20 \uD83D\uDE21 \uD83E\uDD2C "
                        )
                )
            }
            NotificationType.CUSTOM -> {
                notificationBuilder
                    .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                    .setCustomContentView(
                        RemoteViews(
                            packageName,
                            R.layout.view_custom_notification
                        ).apply {
                            setTextViewText(R.id.title, title)
                            setTextViewText(R.id.message, message)
                        }
                    )
            }

        }
        return notificationBuilder.build()
    }

    //앱 시작시점에 채널 생성하는 것을 권장
    //but notification notify 되기 전에만 만들어 놓으면 됨
    private fun createNotificationChannel() {
        //오레오 이상일 경우 채널 생성(Android8.0, api26)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            channel.description = CHANNEL_DESCRIPTION

            //채널을 노티피케이션 매니저에 추가
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
    }

    //다국어 처리를 하고 싶으면 stream으로 옮겨서 번역을 하는 리소스 사용할 것
    companion object {
        private const val CHANNEL_NAME = "Emoji Party"
        private const val CHANNEL_DESCRIPTION = "Emoji Party를 위한 채널"
        private const val CHANNEL_ID = "Channel id"
    }
}