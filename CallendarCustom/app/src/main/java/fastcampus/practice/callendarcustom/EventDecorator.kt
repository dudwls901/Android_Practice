package fastcampus.practice.callendarcustom

import android.app.Activity
import android.content.ContentValues
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan

class EventDecorator(color: Int, dates: Collection<CalendarDay>?, context: Activity) :
    DayViewDecorator {
    private val drawable: Drawable = context.resources.getDrawable(R.drawable.more)
    private val color: Int = color
    private val dates: HashSet<CalendarDay> = HashSet(dates)
    override fun shouldDecorate(day: CalendarDay): Boolean {
        if(dates.contains(day)){
            Log.e(ContentValues.TAG, "shouldDecorate: $day ${dates.contains(day)}" )

        }
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        view.setSelectionDrawable(drawable)
        // 선택해놓은 날짜들의 밑 점 크기, 색상
        view.addSpan(object :  DotSpan(10f, color){})
    }

}