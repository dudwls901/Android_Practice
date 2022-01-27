package com.kyj.fastcampus_firebasepractice

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.messaging.FirebaseMessaging
import com.kyj.fastcampus_firebasepractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFirebase()
        updateResult()

    }
    //MyFirebaseMessagingService의 createNotification에서 intent 플래그를 싱글탑으로 했기 때문에
    //B화면에서 B를 다시 킨 경우 onCreate가 아니라 onNewIntent가 호출되니 재정의
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        //어떤 타입으로 실행
        //기존의 onCreate로 가져온 인텐트가 담겨 있기 때문에
        //새로 들어온 인텐트로 교체해주기
        setIntent(intent)
        updateResult(true)
    }

    private fun initFirebase(){
        //현재 등록된 토큰 가져오기 토큰 가져오는 것은 태스크가 발생되기 때문에 리스너를 통해 받아야 함
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if(task.isSuccessful){
                val token = task.result
                binding.FirebaseTokenTextView.text = token
            }
        }
    }

    //앱이 꺼져있다가 실행됐는지, 앱이 켜져있었는데 노티피케이션 눌러서 갱신을 했는지
    //다국어 관련 경고
    @SuppressLint("SetTextI18n")
    private fun updateResult(isNewIntent: Boolean = false){
        binding.resultTextView.text = (intent.getStringExtra("notificationType") ?: "앱 런처") + if(isNewIntent){
            "(으)로 갱신했습니다."
        }
        else{
            "(으)로 실행했습니다."
        }
    }
}