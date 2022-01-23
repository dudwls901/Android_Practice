package fastcampus.chapter1.fastcampus_pomodorotimer

import android.annotation.SuppressLint
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatDelegate
import fastcampus.chapter1.fastcampus_pomodorotimer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var currentDownTimer: CountDownTimer? = null

    //빌더 패턴
    private val soundPool = SoundPool.Builder().build()

    private var tickingSoundId: Int? = null

    private var bellSoundId: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        bindViews()
        initSounds()
    }

    override fun onResume() {
        super.onResume()
        //soundPool.resume()
        soundPool.autoResume()
    }

    override fun onPause() {
        super.onPause()
        //특정 스트림 id로 재생되고 있는 스트림을 지정하여 종료
//        soundPool.pause()
        //모든 활성화된 스트리밍 다pause
        soundPool.autoPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        //더 이상 앱을 사용하지 않을 때 사운드풀을 메모리에서 해제
        soundPool.release()
    }

    /*
    SoundPool 오디오 사운드를 재생하고 관리하는 클래스
    오디오 파일을 메모리에 로드해서 비교적 바르게 재생하도록 도와줌
    긴 파일을 메모리에 올리긴 부담 있어서 짧은 파일만 올릴 수 있게 제약이 있음
    raw에 저장된 mp4파일이 디바이스의 리소스가 되기 때문에, 특정 앱에서만 이 리소스를 가지고 있는 게 아니라
    디바이스에서 가지고 있는 리소스를 요청하는 것이기 때문에 앱 내에서 따로 pause요청을 하지 않으면 앱을 종료해도
    사운드가 계속 재생됨 -> onPause에서 꺼주기
    사운드 파일이나 미디어 파일들은 비용이 높음 -> 많은 메모리 차지 -> 되도록 사용하지 않는 것이 명확해졌을 때 메모리에서 해제 필수
    */
    private fun initSounds() {
        //priority는 의미 없음, 나중에 호환성을 위해 일단 1 넣으라고 권장
        tickingSoundId = soundPool.load(this, R.raw.timer_ticking, 1)
        bellSoundId = soundPool.load(this, R.raw.timer_bell, 1)
    }

    //각각의 뷰의 리스너와 실제 로직을 연결
    private fun bindViews() {
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            //seekbar, progress, fromUser
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                //fromUser가 건드렸을 때만 업데이트
                //if문 없으면 업데이트 시크바에서 시크바의 프로그래스를 조작해서 onProgresschanged를 호출함
                //그래서 00초로 고정해버림 -> 몇 초간 00으로 있다가 58부터 시작함
                if (p2) {
                    updateRemainTime(p1 * 60 * 1000L)
                }
            }


            override fun onStartTrackingTouch(p0: SeekBar?) {
                //현재 진행중인 카운트다운타이머가 있으면 캔슬
                stopCountDown()
            }
            //시크바 움직이다가 떼는 순간
            override fun onStopTrackingTouch(p0: SeekBar?) {
                //카운트를 애초에 0에 놓는 경우 사운드가 진행되는 이슈 대응
                if (binding.seekBar.progress == 0) {
                    stopCountDown()
                } else {
                    startCountDown()
                }
            }
        })
    }

    private fun startCountDown() {
        //seekBar의 progress는 분 단위이기 때문에 milliseconds로 변경
        currentDownTimer =
            createCountDownTimer(binding.seekBar.progress * 60 * 1000L).start()
        //null이 아닐 경우로 처리
        tickingSoundId?.let { soundId ->
            //여기서 priority는 사운드풀 내에서의 우선순위, 현재 프로젝트에선 사운드 겹치는 일이 없기 때문에 상관없이 0
            //-1은 무한 루프, rate : 재생 속도도 1f
            soundPool.play(soundId, 1f, 1f, 0, -1, 1f)
        }
    }

    private fun stopCountDown(){
        currentDownTimer?.cancel()
        currentDownTimer = null
        soundPool.autoPause()
    }

    //countDownTimer는 인자로 몇 millisecond뒤에 피니시 할 건지, 얼마 간격으로 틱을 발생시킬건지 인자로 전달 받아서
    //onTick과 onFinish 콜백 호출
    //몇 초뒤에 finish할 건지에 대한 재정의 메서드가 없기 때문에 갱신될 때마다 새로 생성
    //CountDownTimer도 추상 클래스임
    private fun createCountDownTimer(initialMillis: Long) =
        //object 표현식으로 익명 내부 클래스 구현 : 추상 클래스 간편히 사용 -> 추상 메소드는 오버라이딩해주기
        object : CountDownTimer(initialMillis, 1000L) {
            //p0 = millisUntilFinished
            override fun onTick(p0: Long) {
                //text,seekbar 갱신
                updateRemainTime(p0)
                updateSeekbar(p0)

            }

            override fun onFinish() {
                completeCountDown()
            }
        }

    private fun completeCountDown() {
        updateRemainTime(0)
        updateSeekbar(0)
        soundPool.autoPause()
        //loop 0은 한 번만 재생
        bellSoundId?.let { soundId ->
            soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
        }
    }

    private fun updateSeekbar(remainMillis: Long) {
        binding.seekBar.progress = (remainMillis / 1000 / 60).toInt()
    }

    @SuppressLint("SetTextI18n")
    private fun updateRemainTime(remainMillis: Long) {
        val remainSeconds = remainMillis / 1000
        //한자리 수도 0 붙이기
        binding.remainMinutesTv.text = "%02d'".format(remainSeconds / 60)
        binding.remainSecondsTv.text = "%02d".format(remainSeconds % 60)
    }
}
