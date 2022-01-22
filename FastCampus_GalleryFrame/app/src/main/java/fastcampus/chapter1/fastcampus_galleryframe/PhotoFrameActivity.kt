package fastcampus.chapter1.fastcampus_galleryframe

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.concurrent.timer

class PhotoFrameActivity : AppCompatActivity() {

    private val photoList = mutableListOf<Uri>()

    private val photoImageView : ImageView by lazy{
        findViewById(R.id.photoIv)
    }
    private val backgroundPhotoImageView : ImageView by lazy{
        findViewById(R.id.photoIv)
    }

    private var currentPosition = 0

    private var timer : Timer? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photoframe)
        getPhotoUriFromIntent()
        Log.d("tttt","onCreate")
    }

    private fun getPhotoUriFromIntent(){
        val size = intent.getIntExtra("photoListSize",0)

        for(i in 0 until size){
            //null이 아닐 때만 실행
            intent.getStringExtra("photo$i")?.let{
                photoList.add(Uri.parse(it))
            }
        }
    }

    //5초에 한 번 전환
    private fun startTimer(){
        //앱 종료 이후 타이머는 꺼주기
        //생명주기
        //onstop으로 들어오면 타이머 끄고 onStart로 들어오면 다시 타이머 재생
        timer = timer(period = 5 *1000){
            //메인쓰레드로 전환
            runOnUiThread {
                Log.d("tttt","5초 지나감")
                val current = currentPosition
                val next = (currentPosition+1) % photoList.size

                backgroundPhotoImageView.setImageURI(photoList[current])
                //애니메이션 투명도 0~1 fadein
                photoImageView.alpha = 0f
                photoImageView.setImageURI(photoList[next])
                photoImageView.animate()
                    .alpha(1.0f)
                    .setDuration(1000)
                    .start()
                currentPosition=next
            }
        }
    }

    override fun onStop() {
        super.onStop()
        timer?.cancel()
        Log.d("tttt","onStop")
    }

    //oncreate는 한 번만 호출되기 때문에 onstop에서 다시 onstart에서 오는경우는 대응 불가
    //고로 startTimer를 onStart에서 호출
    override fun onStart() {
        super.onStart()
        startTimer()
        Log.d("tttt","onStart")
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
        Log.d("tttt","onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("tttt","onRestart")
    }

    override fun onPause() {
        super.onPause()
        Log.d("tttt","onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.d("tttt","onResume")

    }

}