![Fastcampus_TodaySentence](https://user-images.githubusercontent.com/66052467/151644083-f044a6d6-ca7e-4b75-ad3c-15f46c1487de.gif)

# 오늘의 명언
## Stack

- Firebase Remote Config
  - 코드 수정 없이 명언 추가 가능
  - 코드 수정 없이 이름 숨기기 가능
  - Json 파싱
- ViewPager2(JetPack)
  - 무한 스와이프(페이징)
  - 인디케이터 

## 기능
- 좌우로 페이징하여 명언 띄우기

---
## 📌Firebase Remote Config(원격 구성)
- 앱을 업데이트하지 않아도 하루 활성자 수 제한없이 앱의 동작과 모양을 변경할 수 있는 툴
- Firebase Console에 remote config 값을 지정
  - 매개변수 최대 2000개, 조건 최대 500개, value 문자열 최대 80만 길이까지 가능 
- 서버에서 설정한 값을 getch하면 곧바로 값이 보이는 게 아님
- 매개변수값 가져오기, 캐싱 등의 중요한 작업은 클라이언트 라이브러리에서 처리, 새 값이 활성화돼서 UX에 영향을 주는 시점은 개발자가 제어
- 앱 업데이트는 Remote Config 사용 X -> 앱의 신뢰성을 해칠 수 있음
- 원격 구성 매개변수 키가 암호화 되어있지 않으니 매개변수에(Remote Config에) 기밀 데이터 저장하지 말 것
- 앱 스토어 올릴 때 리젝당한 경우 일단 검수 완료되게끔 작업해놓고 검수 후 다시 활성화 시키는 꼼수 금지

### Use Case
#### 1. 프로모션(가장 많이 사용)
- 이 타이밍에 어떤 문구나 이미지를 보여주고 싶을 때, 매번 이거 때문에 업데이트를 할 수 없으니<br>
  Remote Config를 통해 이미지, 안내문자, 홍보문자 변경 + 시간 설정 가능
#### 2. 비율 출시 메커니즘
- 새로운 기능 출시, 업데이트시 바로 100% 사용자에게 배포하기는 부담
- -> 조건 %달아서 10% 사용자에게 노출 -> 50% -> 100% 점진적으로 노출하여 실제로 동작하는지 파악 가능
#### 3. AB테스트
- 제한된 테스트 그룹에서의 새 기능테스트
- A랑 B그룹에 각각 독립적으로 다른 환경 제공해서 비교
<!--  ### ✅ Vector Assets  -->
<!--   - 다양한 해상도, 다중 밀도에 대응하기 위한 것 -->
#### 4. JSON을 사용해 여러가지 속성값들을 한 번에 수정

### 로딩 전략
- 서버에서 fetch 해오는 타이밍이랑 이 fetch해온 값을 실제로 액티베이트해서 앱에 보여주는 타이밍이 다르기 때문에 여러 전략 필요
- 사용자가 보고있는데 UI가 바뀌는 현상 지양
- Remote Config를 대량으로 요청하면 서버에서 블락할 수도 있으니 제한 사항 확인, 최소한의 요청 필요
- SDK 17.0.0 이하 버전에서는 60분동안 가져오기 요청 5회 제안(넘으면 FirebaseRemoteConfigFetchThrottledException)
- 이후 버전에선 늘어남
- -> 개발 단계에선 반복적으로 테스트하기 위해 임시로 가져오기 간격 최솟값을 설정 가능 (0으로 낮게 설정)
- 기본 값은 12시간(실제 배포하여 사용자들이 사용할 땐 기본값인 12시간으로 해야 한다)
- Remote Config 이슈 발생 시 Firebase Status Dashboard에서 Remote Config에 대한 상태가 정상인지 확인
- 
#### 1. 로드시 가져와서 활성화
- 앱 처음 시작할 때 FetchAndActivate로 불러온 다음에 Activate시켜서 값 업데이트
- UI 모양이 크게 변경되지 않는 구성에 적합
#### 2. 로딩 화면 뒤에서 활성화 (가장 무난)
- 로딩 인디케이터 돌려놓고 fetch 다 되면 보여주기
- 앱 시작시 스플래시 뜰 때 Remote Config fetch해오고 Activate시키는 전략
- fetch가 네트워크 상황에 따라 오래 걸릴 수 있기 때문에 ~초 이상 응답없으면 fetch하지 않고 그냥 넘어가기(TimeOut)
#### 3. 다음 시작시 새 값을 로드
- 앱 시작시 바로 fetch하지만 Activate는 하지 않음
- 내부적으로 가지고 있다가 그 다음 앱을 실행할 때 곧바로 Activate
- 변경되는 값을 fetch하고 기다리는 상황이 없기 때문에 바로 대기없이 반영 가능
- 좀 더 원활한 반영
- but 사용자가 최소 앱을 두 번 실행해야 반영되기 때문에 급하게 반영되어야 하는 데이터엔 사용 x
 
### Use Flow
1.0 파이어베이스 프로젝트 생성 <br>
1.1 ApplicationId 복사해서 패키지 네임에 넣기 <br>
1.2 디펜던시, 플러그인, google-services.json 삽입 등 설정 <br>
2. Remote Config Console에서 JSON데이터 설정 <br>
3. 퍼블리싱(게시) <br>
4. 액티비티에서 가져오기 간격 설정 후 FetchAndActivate호출 성공시 실제 키 값을 통해 데이터 받아오기 <br>
5. 받은 JSON 데이터를 파싱 <br>
6. 어댑터로 값 전달해서 화면에 띄우기 <br>

### ✅ Structure
![image](https://user-images.githubusercontent.com/66052467/151644301-ee49945c-952b-4154-a79b-3cc34196fc88.png) <br>

 
 <details markdown="1">
<summary>✅ MainActivity.kt </summary>
<br>
<pre>
<code>
 package fastcampus.chapter1.fastcampus_recorder

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import fastcampus.chapter1.fastcampus_recorder.databinding.ActivityMainBinding
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {


    private lateinit var binding : ActivityMainBinding

    //state tracking 해야 함 고로 전역 변수
    //세터를 이용해 새로운 스테이트를 할당할 때마다 아이콘 변경하기
    //커스텀 세터, State의 프로퍼티 BEFORE_RECORDING의 세터를 커스텀
    private var state = State.BEFORE_RECORDING
        set(value){
            field = value
            //녹음이 재생되는 시점, 녹음이 완료되는 시점에 restBtn이 활성화됨
            binding.resetBtn.isEnabled = (value == State.AFTER_RECORDING || value == State.ON_PLAYING)
            binding.recordBtn.updateIconWithState(value)
        }
    private val requiredPermissions = arrayOf(android.Manifest.permission.RECORD_AUDIO)

    //미디어레코더는 정지하게 되면 다시 초기화해야 하기 때문에(다시 Initial단계로 감)
    //사용하지 않을 때는 release(메모리에서 해제)하고 null로 두는 편이 메모리 관리에 수월
    private var recorder: MediaRecorder? = null

    private var player: MediaPlayer? = null

    private val recordingFilePath: String by lazy{
        "${externalCacheDir?.absolutePath}/recording.3gp"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //앱 시작 시 바로 권한 요청
        requestAudioPermission()
        initViews()
        bindViews()
        initVariables()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        //true or false 바로 저장
        val audioRecordPermissionGranted = requestCode == REQUEST_RECORD_AUDIO_PERMISSION &&
                grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED

        //에뮬레이터 정상 작동 API 30
        // 내 폰 onRequestPermissionsResult 빠르게 두 번 호출돼서 권한 설정 전에 앱 종료됨 API 29
        // 이유 : 다크모드 무시 설정할 시 이렇게 됨
        //거절할 경우 그냥 종료
        //실제로 이렇게 하진 말자.. 실습 편의를 위해 ㅎㅎ
        if(!audioRecordPermissionGranted){
            finish()
        }
    }

    private fun requestAudioPermission(){
        requestPermissions(requiredPermissions, REQUEST_RECORD_AUDIO_PERMISSION)
    }

    private fun initViews(){
        binding.recordBtn.updateIconWithState(state)
    }

    private fun bindViews(){
        binding.soundVisualizerView.onRequestCurrentAmplitude ={
            recorder?.maxAmplitude ?: 0
        }
        binding.recordBtn.setOnClickListener{
            when(state){
                State.BEFORE_RECORDING -> {
                    startRecording()
                }
                State.ON_RECORDING -> {
                    stopRecording()
                }
                State.AFTER_RECORDING -> {
                    startPlaying()
                }
                State.ON_PLAYING -> {
                    stopPlaying()
                }
            }
        }
        binding.resetBtn.setOnClickListener {
            stopPlaying()
            state = State.BEFORE_RECORDING
            binding.soundVisualizerView.clearVisualization()
            binding.recorTimeTv.clearCountTime()
        }
    }

    private fun initVariables(){
        state = State.BEFORE_RECORDING
    }

    private fun startRecording(){
        recorder = MediaRecorder().apply{
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
/*            현재 프로젝트는 따로 저장은 안하고 녹음하고 확인만
            앱에서 접근할 수 있는 storage는 Internal이랑 외부가 있는데
            Internal(내부)는 용량이 한계가 있는데 녹음은 1분~3시간 12시간 등 용량이 클 수 있기 때문에
            Internal로 저장하기보다 외부 storage에 저장해야 한다.
            cacheDirectory에 접근해서 임시적으로 녹음 파일을 저장하되 이 앱이 지워지거나
            안드로이드 디바이스에서 용량확보할 때 캐시에있는 것은 쉽게 날라갈 수 있음
            실습이니까 그냥 cacheDirectory에 하는 거
            그래도 녹음 기능이라 외부 저장소에 저장은 함*/
            setOutputFile(recordingFilePath)
            prepare()
        }
        recorder?.start()
        state = State.ON_RECORDING
        binding.soundVisualizerView.startVisualizing(false)
        binding.recorTimeTv.startCountUp()
    }

    //stop, 메모리 해제, recorder에는 null
    private fun stopRecording(){
        recorder?.run{
            stop()
            release()
        }
        recorder =null
        state = State.AFTER_RECORDING
        binding.soundVisualizerView.stopVisualizing()
        binding.recorTimeTv.stopCountUp()
    }

    private fun startPlaying(){
         player = MediaPlayer().apply{
             setDataSource(recordingFilePath)
             //prepareAsync는 비동기 처리
             //현재 앱에선 로컬에서 파일을 가져오는 것이기 때문에 파일이 엄청 큰 거 아니면 일반적으로 그냥 prepare로도 충분
             prepare()
         }
        player?.setOnCompletionListener {
            stopPlaying()
            state = State.AFTER_RECORDING
        }
        player?.start()
        state = State.ON_PLAYING
        binding.soundVisualizerView.startVisualizing(true)
        binding.recorTimeTv.startCountUp()
    }

    //현재 앱에선 stop()안 가고 그냥 바로 release
    private fun stopPlaying(){
        player?.release()
        player = null
        state = State.AFTER_RECORDING
        binding.soundVisualizerView.stopVisualizing()
        binding.recorTimeTv.stopCountUp()
    }

    companion object {
        //유니크한 값으로 임의 설정
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 201
    }
}
 </code>
</pre>
</details>

 <details markdown="1">
<summary>✅ State.kt (Enum Class) </summary>
<br>
<pre>
<code>
 package fastcampus.chapter1.fastcampus_recorder

//상태 값에 따른 다른 ui 보여주기
enum class State {
    BEFORE_RECORDING,
    ON_RECORDING,
    AFTER_RECORDING,
    ON_PLAYING
}
 </code>
</pre>
</details>

 <details markdown="1">
<summary>✅ SoundVisualizerView.kt (CustomView) </summary>
<br>
<pre>
<code>
 package fastcampus.chapter1.fastcampus_recorder

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

class SoundVisualizerView(
    context : Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    var onRequestCurrentAmplitude: (() -> Int)? =null

    //amplitude : 증폭
    //ANTI_ALIAS_FLAG : 계단화 방지, 도트 이미지 찍은 것처럼 각지게 그려지는 것을 방지하는 플래그 -> 곡선이 좀 부드럽게 됨
    private val amplitudePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply{
        //고정 값들, length(세로)는 볼륨에 따라 가변
        color = context.getColor(R.color.purple_500)
        strokeWidth = LINE_WIDTH
        //라인의 양 끝을 둥그렇게
        strokeCap = Paint.Cap.ROUND
    }
    private var drawingWidth : Int = 0
    private var drawingHeight : Int = 0
    private var drawingAmplitudes: List<Int> = emptyList()
    private var isReplaying: Boolean = false
    private var replayingPosition : Int = 0

    //20ms마다 반복
    private val visualizeRepeatAction: Runnable = object : Runnable{
        override fun run() {
            //Amplitude, Draw

            if(!isReplaying) {
                //MainActivity에 Viewbind를 통해 정의한 onRequestCurrentAmplitude를 호출
                //onRequestCurrentAmplitude는 recorder의 maxAmplitude를 반환
                val currentAmplitude = onRequestCurrentAmplitude?.invoke() ?: 0
                drawingAmplitudes = listOf(currentAmplitude) + drawingAmplitudes
            }
            else{
                replayingPosition++
            }
            //데이터가 추가됐을 때 invalidate를 추가해야 onDraw를 다시 호출하게 됨 -> 뷰 갱신을 위해 사용
            invalidate()
            handler?.postDelayed(this, ACTION_INTERVAL)
        }
    }



    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        drawingWidth = w
        drawingHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas ?: return

        //뷰의 위아래 진폭을 알아야 하고, 어떻게 그릴지
        //뷰의 우측부터 그리기
        val centerY = drawingHeight/2f
        var offsetX = drawingWidth.toFloat()

        //진폭값들을 리스트로

        //let + forEach
        drawingAmplitudes.let { amplitudes ->
            if(isReplaying) {
                amplitudes.takeLast(replayingPosition)
            }else{
                amplitudes
            }
        }.forEach{amplitude ->
            //진폭이 맥스일 때 꽉차는 것보다 0.8배 정도가 이쁨
            val lineLength = amplitude / MAX_AMPLITUDE * drawingHeight * 0.8f

            offsetX -= LINE_SPACE
            //뷰의 왼쪽 영역보다 더 밖에 있다 -> 화면을 벗어났다 ->그리지 않고 루프문 탈출
            if(offsetX <0) return@forEach

            canvas.drawLine(
                offsetX,
                centerY - lineLength /2f,
                offsetX,
                centerY + lineLength /2f,
                amplitudePaint
            )

        }

    }

    fun startVisualizing(isReplaying : Boolean){
        this.isReplaying = isReplaying
        handler?.post(visualizeRepeatAction)
    }

    fun stopVisualizing(){
        replayingPosition = 0
        handler?.removeCallbacks(visualizeRepeatAction)
    }

    fun clearVisualization(){
        drawingAmplitudes = emptyList()
        invalidate()
    }

    companion object{
        private const val LINE_WIDTH = 10F
        private const val LINE_SPACE = 15F
        //볼륨의 최댓값 short의 max 32767, toFloat()으로 0으로 나누는 일을 방지
        private const val MAX_AMPLITUDE = Short.MAX_VALUE.toFloat()
        private const val ACTION_INTERVAL = 20L
    }
}
 </code>
</pre>
</details>

 <details markdown="1">
<summary>✅ RecordButton.kt (Custom View)</summary>
<br>
<pre>
<code>
 package fastcampus.chapter1.fastcampus_recorder

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatImageButton

/*
Context와 AttributeSet을 파라미터로 전달해야 레이아웃에디터(인드로이드스튜디오의 레이아웃 편집기)에서
RecordButton을 추가하고 속성을 수정할 수 있음
 */
//AppCompat : 이전 버전의 안드로이드에 대한 호환성 기존 클래스를 래핑해서 이전 버전에서도 새로 출시한 기능들을 정상적으로 동작하게 도와주는 라이브러리
//xml에선 그냥 TextView로 써도 xml 내부에 정의된 텍스트뷰를 자동으로 AppCompat으로 매핑할 수 있는 클래스가 있는 경우 자동으로 매핑해줌
//코드에선 이러한 기능이 없기 때문에 직접 AppCompat을 붙인다.
//최근에는 AppCompat을 Material 디자인 가이드에 맞춰서 클래스를 매핑시켜 전환시켜주는 기능도 제공하는데 현재 이미지버튼은 해당 안 됨
class RecordButton(context: Context, attrs: AttributeSet) : AppCompatImageButton(context, attrs) {

    //xml에서 하는 것보다 재사용성bb
    init{
        setBackgroundResource(R.drawable.shape_oval_button)
    }

    //State에 따라 레코드 버튼 변경
    fun updateIconWithState(state : State){
        when(state){
            State.BEFORE_RECORDING ->{
                setImageResource(R.drawable.ic_record)
            }
            State.ON_RECORDING -> {
                setImageResource(R.drawable.ic_stop)
            }
            State.AFTER_RECORDING -> {
                setImageResource(R.drawable.ic_play)
            }
            State.ON_PLAYING -> {
                setImageResource(R.drawable.ic_stop)
            }
        }
    }
}
 </code>
</pre>
</details>

 <details markdown="1">
<summary>✅ CountUpView.kt </summary>
<br>
<pre>
<code>
 package fastcampus.chapter1.fastcampus_recorder

import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CountUpView(
    context: Context,
    attrs: AttributeSet? = null
): AppCompatTextView(context, attrs) {

    private var startTimeStamp: Long = 0L

    //1초마다 반복
    //timstamp랑 현재 시간 비교해서 몇 초가 흘렀는지 계산 후 텍스트 변경
    private val countUpAction : Runnable = object: Runnable{
        override fun run() {
            val currentTimeStamp = SystemClock.elapsedRealtime()

            val countTimeSeconds = ((currentTimeStamp - startTimeStamp)/1000L).toInt()
            updateCountTime(countTimeSeconds)

            handler?.postDelayed(this, 1000L)
        }
    }

    fun startCountUp(){
        startTimeStamp = SystemClock.elapsedRealtime()
        handler?.post(countUpAction)
    }

    fun stopCountUp(){
        handler?.removeCallbacks(countUpAction)
    }

    fun clearCountTime(){
        updateCountTime(0)
    }

    //텍스트에 반영
    private fun updateCountTime(countTimeSeconds : Int){
        val minutes = countTimeSeconds / 60
        val seconds = countTimeSeconds % 60

        text = "%02d:%02d".format(minutes, seconds)
    }
}
 </code>
</pre>
</details>


