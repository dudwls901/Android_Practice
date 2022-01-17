package fastcampus.chapter1.fastcampus_secretdiary

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings.Global.putString
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity : AppCompatActivity() {

    //안드로이드 os에서 제공해주는 기능
    //Looper.getMainLooper() : 핸들러를 메인 스레드에 연결 ->뷰에도 핸들러 이용 가능 (포스트,포스트딜레이)
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)
        val diaryEt = findViewById<EditText>(R.id.diaryET)

        val detailPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)

        diaryEt.setText(detailPreferences.getString("detail",""))


        //텍스트가 변경될 때마다 프리퍼런스에 저장
        //하지만 너무 빈번하게 저장되니 쓰레드를 이용해 뭔가 작성하다가 멈칫했을 때 저장시키기(자동 저장)
        //Runnable
        //쓰레드에서 일어나는 기능 구현
        val runnable = Runnable {
            getSharedPreferences("diary",Context.MODE_PRIVATE).edit{
            //디폴트 commit = false, 고로 apply로 적용
                putString("detail",diaryEt.text.toString())
                Log.d("DiaryActivity","TextChanged :: SAVE!! : ${diaryEt.text}")
            }
        }
        //텍스트가 연속적으로 변경되면(0.5초 이내에 계속 변경되면) 기존 러너블 실행 요청은 remove되고,
        //나중에 0.5초 이상 멈춰있을 때 러너블 제대로 실행됨
        diaryEt.addTextChangedListener{
            //핸들러 이용
            //스레드 열었을 때 UI에서 처리되는 걸 UI(메인)스레드
            //일반 스레드 = 워커(작업)스레드
            //스레드 관리할 때 UI스레드랑 일반 스레드 연결 필요
            //메인 스레드가 아닌 곳에선 UI를 변경하는 작업이 불가하기 때문에
            //핸들러로 메인스레드와 작업스레드를 연결

            //0.5초 이전에 아직 실행되지 않고 남은 러너블이 있다면 지워주기
            //있으면 지우고 없으면 말고
           handler.removeCallbacks(runnable)
            //0.5초에 한 번씩 러너블 실행
            handler.postDelayed(runnable, 500)
        }
    }
}