![FastCampus_Recorder](https://user-images.githubusercontent.com/66052467/150816587-83518c63-b67c-48e7-84c8-a5a9e558c0b9.gif)


# 녹음기
## Stack

- ConstraintLayout
- CustomView 
- Override onDraw()
  - canvas
  - paint
  - Override onSizeChanged
- requestPermissions
- MediaRecorder
- MediaPlayer

## 기능
- 녹음 + 재생
- 녹음할 때, 재생할 때 음성을 커스텀뷰에 onDraw로 비쥬얼라이징
- 녹음, 재생 시간 커스텀 텍스트뷰에 비쥬얼라이징

---
- Custom Drawing
  - Override onDraw(canvas: Canvas?) : canvas 객체 전달 받아서 drawing함으로써 뷰에 그릴 수 있다.
  - canvas : 무엇읕 그릴지 (onDraw의 인자로 받음)
  - Paint : 어떻게 그릴지 (onDraw는 많이 호출되기 때문에 비용이 높은 Paint 객체 생성 행위를 onDraw 밖에 생성해주자)
  - 실제로 그려야할 영역의 사이즈를 정확히 파악, 다양한 사이즈에 대비해야 함
  - Override onSizeChanged()로 그려야할 영역의 사이즈를 미리 파악하자. 


<!--  ### ✅ Vector Assets  -->
<!--   - 다양한 해상도, 다중 밀도에 대응하기 위한 것 -->

<!-- ### ✅ Structure
![image](https://user-images.githubusercontent.com/66052467/150817303-8cc6fa32-9444-4efd-acbc-e92d287734e4.png) <br>
 -->
 
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


