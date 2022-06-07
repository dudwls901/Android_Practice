package fastcampus.practice.callendarcustom.second.view.decorator

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import androidx.core.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import fastcampus.practice.callendarcustom.R

class DotDecorator(context: Activity, private val dates: List<CalendarDay>) : DayViewDecorator {
    val color = ContextCompat.getColor(context,R.color.red)
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        //뭘로 받아올까?
        if(dates.contains(day)){
            Log.e(TAG, "shouldDecorate: $day ${dates.contains(day)}" )
        }
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        Log.e(TAG, "abc: $" )
        view.addSpan(object: DotSpan(10f,color){})
    }
}