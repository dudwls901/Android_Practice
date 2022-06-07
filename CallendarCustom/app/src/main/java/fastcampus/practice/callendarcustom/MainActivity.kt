package fastcampus.practice.callendarcustom

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.prolificinteractive.materialcalendarview.*
import fastcampus.practice.callendarcustom.databinding.ActivityMainBinding
import fastcampus.practice.callendarcustom.second.view.MaterialCalendarActivity
import java.util.*
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var time : String
    private lateinit var kcal : String
    private lateinit var menu : String
    private lateinit var oneDecorator: OneDayDecorator
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        oneDecorator = OneDayDecorator(CalendarDay.today())

        //todo 캘린더 밑 리사이클러뷰
        initViews()
    }

    private fun initViews() = with(binding){
        button.setOnClickListener {
            startActivity(Intent(this@MainActivity, MaterialCalendarActivity::class.java ))
        }


        val result = arrayOf("2022,02,18", "2022,02,19", "2022,02,20", "2022,02,21")
        ApiSimulator(result).executeOnExecutor(Executors.newSingleThreadExecutor())
        calendarView.state().edit()
            .setFirstDayOfWeek(Calendar.SUNDAY)//캘린더 시작 요일
            .setMinimumDate(CalendarDay.from(2022, 0, 1))//캘린더 시작 날짜
            .setMaximumDate(CalendarDay.from(2022, 11, 31))//캘린더 끝 날짜
            .setCalendarDisplayMode(CalendarMode.MONTHS) // 월 달력, 주 달력
            .commit()

        //decorator 생성해서 커스텀
        calendarView.addDecorators(
            oneDecorator,
            SaturdayDecorator(),
            SundayDecorator(),
            MySelectorDecorator(this@MainActivity)
        )
        calendarView.setOnDateChangedListener { widget, date, selected ->
            val year = date.year
            val month = date.month + 1
            val day = date.day
            Log.i("widgetStyle", widget.currentDate.toString())
            Log.i("widgetStyle", widget.state().toString())
            Log.i("Year test", year.toString() + "")
            Log.i("Month test", month.toString() + "")
            Log.i("Day test", day.toString() + "")
            val shot_Day = "$year,$month,$day"
            Log.i("shot_Day test", shot_Day + "")
//            calendarView.isDynamicHeightEnabled =true
//            widget.selectionMode = SELECTION_MODE_RANGE
//            widget.s
//            calendarView.invalidateDecorators()
            calendarView.selectedDate = date
//            calendarView.state().edit()
            //todo 백그라운드 효과 사이즈 줄이기

//            calendarView.clearSelection()

            Toast.makeText(this@MainActivity, shot_Day, Toast.LENGTH_SHORT).show()
        }
    }



    @SuppressLint("StaticFieldLeak")
    inner class ApiSimulator(var TimeResult: Array<String>) :
        AsyncTask<Void?, Void?, List<CalendarDay>>() {
        protected override fun doInBackground(vararg p0: Void?): List<CalendarDay>? {
            try {
                Thread.sleep(500)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            val calendar = Calendar.getInstance()
            val dates = ArrayList<CalendarDay>()

            /*특정날짜 달력에 점표시해주는곳*/
            /*월은 0이 1월 년,일은 그대로*/
            //string 문자열인 Time_Result 을 받아와서 ,를 기준으로짜르고 string을 int 로 변환
            for (i in TimeResult.indices) {
                val day = CalendarDay.from(calendar)
                val time = TimeResult[i].split(",").toTypedArray()
                val year = time[0].toInt()
                val month = time[1].toInt()
                val dayy = time[2].toInt()
                dates.add(day)
                Log.e("ttttt", "day  $day")
                Log.e("ttttt", year.toString() + month + dayy + "")
                calendar.set(year,month-1,dayy)
            }
            return dates
        }

        override fun onPostExecute( calendarDays: List<CalendarDay>) {
            super.onPostExecute(calendarDays)
            if (isCancelled()) {
                return
            }
            binding.calendarView.addDecorator(
                EventDecorator(
                    R.color.purple_200,
                    calendarDays,
                    this@MainActivity
                )
            )
        }

    }
}


