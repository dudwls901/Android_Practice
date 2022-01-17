package fastcampus.chapter1.fastcampus_secretdiary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {


    private val numberPicker1 by lazy{
        findViewById<NumberPicker>(R.id.numberPicker1).apply{
            minValue = 0
            maxValue = 9
        }
    }

    private val numberPicker2 by lazy{
        findViewById<NumberPicker>(R.id.numberPicker1).apply{
            minValue = 0
            maxValue = 9
        }
    }
    private val numberPicker3 by lazy{
        findViewById<NumberPicker>(R.id.numberPicker1).apply{
            minValue = 0
            maxValue = 9
        }
    }

    private val openBtn by lazy{
        findViewById<AppCompatButton>(R.id.openBtn)
    }
    private val changePWBtn by lazy{
        findViewById<AppCompatButton>(R.id.changePWBtn)
    }

    private var changePWMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //호출로 초기화해주기
        numberPicker1
        numberPicker2
        numberPicker3

        //다이어리 열기
        openBtn.setOnClickListener {
            if(changePWMode){
                Toast.makeText(this,"비밀번호 변경 중입니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //비밀번호 비교해야함
            //MODE_PRIVATE : 프리퍼런스라는 파일을 다른 앱과 쉐어할 수 있지만 mode_private로 이 앱에서만 사용할 수 있게 함
            val pwPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)

            val pwFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            //초기번호 000
            if (pwPreferences.getString("password","000").equals(pwFromUser)){
                //패스워드 성공
                startActivity(Intent(this,DiaryActivity::class.java))

            }
            //실패
            else{
                showErroAlertDialog()
            }
        }
        changePWBtn.setOnClickListener {

            //MODE_PRIVATE : 프리퍼런스라는 파일을 다른 앱과 쉐어할 수 있지만 mode_private로 이 앱에서만 사용할 수 있게 함
            val pwPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val pwFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"
            //번호를 저장
            if(changePWMode){
                pwPreferences.edit(commit=true){
                    //commit, apply방식
                    //commit=false 디폴트
                    //commit : 이 파일이 저장될 때까지 ui를 멈추고 기다림
                    //apply : 비동기적으로 저장
                    //긴 작업이 아니니 그냥 commit
                    putString("password","$pwFromUser")
                }
                changePWMode=false
                changePWBtn.setBackgroundColor(Color.BLACK)
            }
            //비밀번호 비교
            else{
                //초기번호 000
                if (pwPreferences.getString("password","000").equals(pwFromUser)){
                    //changePWMode 활성화, 비밀번호 맞는지 체크
                    changePWMode=true
                    Toast.makeText(this, "변경할 패스워드를 입력해주세요",Toast.LENGTH_SHORT).show()
                    changePWBtn.setBackgroundColor(Color.RED)
                }
                //실패
                else{
                    showErroAlertDialog()
                }
            }
        }
    }
    private fun showErroAlertDialog(){
        AlertDialog.Builder(this)
            .setTitle("실패")
            .setMessage("비밀번호가 잘못되었습니다.")
            .setPositiveButton("확인"){ _, _ -> }
            .create()
            .show()
    }
}