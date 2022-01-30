package fastcampus.part3.fastcampus_alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import fastcampus.part3.fastcampus_alarm.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //알람 설정 데이터 시간과 분, 알람을 켰느나 껐느냐 sharedpreperences에 저장
        //이 값들을 실제로 알람이 저장되어있는지 확인하여 데이터를 보정
        //step0 뷰를 초기화
        initOnOffButton()
        initChangeAlarmTimeButton()

        //step1 데이터 가져오기
        val model = fetchDataFromSharedPreferences()
        renderView(model)
        //step2 뷰에 데이터를 그려주기
    }

    private fun initOnOffButton() {
        //오프 -> 알람을 제거
        //온 -> 알람을 등록
        //데이터를 저장한다
        binding.onOffButton.setOnClickListener { button ->
            //tag 자체는 오브젝트로 받기 때문에 AlarmDisplayModel인지 모른다
            //as?로 형변환 ?는 형번환에 실패할 수도 있기 때문에
            //null을 반환하면 라벨을 이용하여 setOnClickListener함수에서만 빠져나오기
            val model = button.tag as? AlarmDisplayModel ?: return@setOnClickListener
            //데이터 확인
            val newModel = saveAlarmModel(model.hour, model.minute, model.onOff.not())
            renderView(newModel)

            //온오프에 따라 작업을 처리

            if (newModel.onOff) {
                //켜진 경우 알람을 등록
                //캘린더 객체에 모델에 있는 시간을 저장
                val calendar = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, newModel.hour)
                    set(Calendar.MINUTE, newModel.minute)
                    //현재 시간보다 이전이면 다음날로
                    if (before(Calendar.getInstance())) {
                        add(Calendar.DATE, 1)
                    }
                }
                val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
                val intent = Intent(this, AlarmReceiver::class.java)
                // 브로드캐스트에 등록할 팬딩인텐트를 가져오기
                val pendingIntent = PendingIntent.getBroadcast(
                    this,
                    ALARM_REQUEST_CODE,
                    intent,
                    //기존 인텐트가 있으면 새로운 인텐트로 업데이트
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                //지정한 시간에 하루마다 반복 실행
                //setInexactRepeating : 정시에 실행+반복
                //알람을 보다 정확한 시간에 띄우려면 휴대폰이 그 시간이 되었는지 계속 체크해야해서 자원을 많이 먹음
                //Inexact로 비정확하게 알람 설정
                //정확하게 발생시키려면 setExact()
                //repeating은 없지만 정확한 시간에 알람 설정할 수 있음
                //이걸 반복적으로 하고 싶으면 알람이 울렸을 때 그 다음 알람을 새로 설정
                alarmManager.setInexactRepeating(
                    //RTC_WAKEUP은 실제 시간
                    //ELAPSED_REALTIME_WAKEUP 은 휴대폰 부팅된 이후부터 시간을 잼
                    //만약 지금 시간으로 핸드폰이 부팅된 시간을 가져오고 100초 뒤에 실행하고 싶으면
                    //지금 시간으로 부팅된 시간 + 100초
                    //안드로이드 폰은 전세계적으로 사용하고, 서울에 있더라도 미국 시간을 설정해놨다면 알람이 정확하게 동작하지 않을 수 있음
                    //따라서 ELAPSED_REALTIM_WAKEUP을 권장
                    //이 프로젝트는 Calendar를 사용했기 때문에 RTC_WAKEUP을 사용
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
                )
            } else {
                //꺼진 경우 알람을 제거
                cancelAlarm()
            }
        }
    }

    private fun initChangeAlarmTimeButton() {
        binding.changeAlarmTimeButton.setOnClickListener {
            // 현재 시간을 일단 가져온다
            val calendar = Calendar.getInstance()

            //TimePickDialog 띄워서 시간을 설정하도록 하게끔 하고, 그 시간을 가져와서
            TimePickerDialog(this, { picker, hour, minute ->
                //이미 시간이 설정된 이후에 실행되는 람다
                //데이터를 저장
                val model = saveAlarmModel(hour, minute, false)
                //뷰를 업데이트
                renderView(model)
                //기존에 있던 알람을 삭제
                cancelAlarm()

            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false)
                .show()
        }
    }

    private fun saveAlarmModel(
        hour: Int,
        minute: Int,
        onOff: Boolean
    ): AlarmDisplayModel {
        val model = AlarmDisplayModel(
            hour = hour,
            minute = minute,
            onOff = onOff
        )

//        sharedPreferences를 ktx로 열어서 바로 작성-> commit 없이 바로 저장
//        sharedPreferences.edit{
//
//        }
        //with함수를 이용하여 같이 작성할 수 있는 경우를 묶어서 작업
        //with: edit() 함수에 대한 작업을 with 홤수에 저장하여 사용
        //with함수를 사용했기 때문에 commit() or apply() 을 해주어야 데이터가 저장된다.
        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(ALARM_KEY, model.makeDataForDB())
            putBoolean(ONOFF_KEY, model.onOff)
            commit()
        }


        return model
    }

    private fun fetchDataFromSharedPreferences(): AlarmDisplayModel {
        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        //sharedPreferences.getString은 자바에서 가져오는 거라 Nullable이 내려올 수 있음
        val timeDBValue = sharedPreferences.getString(ALARM_KEY, "9:30") ?: "9:30"
        //getBoolean은 primary 타입으로 null 존재 x
        val onOffDBValue = sharedPreferences.getBoolean(ONOFF_KEY, false)
        val alarmData = timeDBValue.split(':').map { it.toInt() }

        val alarmModel = AlarmDisplayModel(
            hour = alarmData[0],
            minute = alarmData[1],
            onOff = onOffDBValue
        )
        //보정 예외처리
        //sharedpreferences에서 알람이 켜져있다고 되어있는데 실제로 앱에 알람이 등록되어있지 않다면 sharedpreferences의 값을 off로 바꾸기
        //반대로 알람은 등록되어있는데 sharedPreferences에서는 꺼져있는 경우는 값을 on으로 바꾸기
        //알람 등록이 되었는지 확인방법은 broadcast를 가져와서 pendingIntent가 실제로 등록이 ㅗ디어있는지 안 되어있는지 확인

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            ALARM_REQUEST_CODE,
            Intent(this, AlarmReceiver::class.java),
            PendingIntent.FLAG_NO_CREATE //있으면 가져오고 없으면 안 만든다
        )
        if ((pendingIntent == null) && alarmModel.onOff) {
            //알람은 꺼져있는데, 데이터는 켜져있는 경우
            alarmModel.onOff = false
        } else if ((pendingIntent != null) && alarmModel.onOff.not()) {
            //알람은 켜져있는데, 데이터는 꺼져있는 경우
            //알람을 취소함
            pendingIntent.cancel()
        }
        return alarmModel
    }

    private fun renderView(model: AlarmDisplayModel) {
        binding.ampmTextView.apply {
            text = model.ampmText
        }
        binding.timeTextView.apply {
            text = model.timeText
        }
        binding.onOffButton.apply {
            text = model.onOffText
            tag = model
        }
    }

    private fun cancelAlarm() {
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            ALARM_REQUEST_CODE,
            Intent(this, AlarmReceiver::class.java),
            PendingIntent.FLAG_NO_CREATE //있으면 가져오고 없으면 안 만든다
        )
        //알람이 저장되어있지 않다면 pendingIntent가 null일 수도 있음
        pendingIntent?.cancel()
    }

    companion object {
        private const val SHARED_PREFERENCES_NAME = "time"
        private const val ALARM_KEY = "alarm"
        private const val ONOFF_KEY = "onOff"
        private const val ALARM_REQUEST_CODE = 1000
    }
}