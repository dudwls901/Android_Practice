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