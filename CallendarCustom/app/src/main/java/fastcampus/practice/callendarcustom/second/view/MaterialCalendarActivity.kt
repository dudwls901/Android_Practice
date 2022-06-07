package fastcampus.practice.callendarcustom.second.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import fastcampus.practice.callendarcustom.databinding.ActivityMaterialCalendarBinding
import fastcampus.practice.callendarcustom.second.view.decorator.DotDecorator
import fastcampus.practice.callendarcustom.second.view.viewmodel.CalendarViewModel
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.LocalDateTime.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext

class MaterialCalendarActivity : AppCompatActivity(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var binding: ActivityMaterialCalendarBinding

    private val calendarViewModel: CalendarViewModel by viewModels()

    private lateinit var adapter: DateListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaterialCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calendarViewModel = calendarViewModel
        binding.lifecycleOwner = this
//        adapter = DateListAdapter()
//        binding.recyclerView.adapter = adapter
//        adapter.itemCount
        //done 여러 날짜 dot 찍기
        //done 선택한 날짜 효과
        //현재 날짜 표시는 main에서 Done
        val date = Date()
        val localDate = now()

        initButton()
        initCalendar()
        initRefresh()
    }

    private fun initRefresh() = with(binding){
        binding.refreshView.setOnRefreshListener {
//            binding.refreshView.updateViewLayout(binding.calendarView,binding.root,)
            binding.refreshView.isRefreshing = false
        }
    }
    private suspend fun loadData() = withContext(Dispatchers.IO) {
        //서버 데이터 받아왔다 치고
        //뷰모델에 반영해줘야 함
        val selectedDates = arrayOf(
            "2022,02,03",
            "2022,02,04",
            "2022,02,10",
            "2022,02,11",
            "2022,02,12",
        )
        val dates = ArrayList<CalendarDay>()
        for(selectedDate in selectedDates){
            val (year, month, day) = selectedDate.split(',').map{it.toInt()}
            dates.add(CalendarDay.from(year,month-1,day))
        }
        //그냥 실행하면 background thread로 작업돼서 컴파일 에러
        //livedata의 setValue는 백그라운드 스레드로 작업 불가

        withContext(Dispatchers.Main) {
            binding.calendarViewModel?.setDate(dates)
            Toast.makeText(this@MaterialCalendarActivity, "${binding.calendarViewModel?.date?.value}", Toast.LENGTH_SHORT).show()
            binding.calendarView.addDecorator(
                //넘겨줄 데이터 형식을 Livedate로 해야 할지 아니면 변환해서 넘겨줄지??
                //리사이클러뷰 사용한 예제 보고 확인하
                //todo livedata는 동기, 비동기 어케 처리할까 observe, postvalue
                //postValue쓰면 되긴 한데 값이 늦게 적용됨
                //그리고 비동기로 넣을 일이 거의 없음
                //repo에서 데이터 받아오는 경우 백그라운드 스레드로 받아온 데이터를 Live데이터에 대입만 하면 됨
                DotDecorator(this@MaterialCalendarActivity, dates)
            )
        }

    }
    private fun initCalendar() = with(binding) {
        calendarView.state().edit()
            .setFirstDayOfWeek(Calendar.SATURDAY)
            .setMinimumDate(CalendarDay.from(2022, 0, 1))//캘린더 시작 날짜
            .setMaximumDate(CalendarDay.from(2022, 11, 31))//캘린더 끝 날짜
            .setCalendarDisplayMode(CalendarMode.MONTHS) // 월 달력, 주 달력
            .commit()

        launch(coroutineContext){
            loadData()
        }


        //year, month, day 다 int로 다루자
        //Date에 Year month day 넣고 date를 라이브데이터로 사용
        //long type 현재 시간
        //아니면 year,month,day엔티티?

        val current = System.currentTimeMillis()

        //현재 시간 date로 받기
        val date = Date(current)
        Log.d("aaaaa",date.toString())
        //Calendar는 날짜를 다루기 위한 유틸
        val calendar2 = Calendar.getInstance()
        val calendar = Calendar.getInstance()
        //실제 날짜 뷰와 호환되는 calendarDay
        val calendarDay = CalendarDay()

        //setTime으로 설정 가능
        calendar2.time=date
        //혹은 int,int,int
//        calendar.set(year,month-1,dayy)
//calencarView는 디폴트로 오늘 날짜 띄어줌, 월 이동시

        Log.d("showOtherDates","${calendarView.showOtherDates}")
        Log.d("currentDate","${calendarView.currentDate}")
        Log.d("size","${calendarDay} ${calendar[Calendar.DAY_OF_WEEK]} " +
                "${calendar2[Calendar.DAY_OF_WEEK]} ${calendarDay.day} " +
                "${calendar[Calendar.FRIDAY]} ${calendarDay.day} " +
                "${calendar[Calendar.WEDNESDAY]}")
        calendar.set(2022,2,21)

        Toast.makeText(this@MaterialCalendarActivity, calendar.get(Calendar.SUNDAY).toString(), Toast.LENGTH_SHORT).show()



        calendarView.setOnMonthChangedListener { widget, date ->
            Log.d("changedsize", "${calendar[Calendar.DAY_OF_WEEK]} $widget $date")
        }

        calendarView.setOnDateChangedListener { widget, date, selected ->

            Toast.makeText(this@MaterialCalendarActivity, "${date.year} ${date.month} ${date.day} ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initButton() = with(binding) {
        button.setOnClickListener {
            finish()
        }
    }
}