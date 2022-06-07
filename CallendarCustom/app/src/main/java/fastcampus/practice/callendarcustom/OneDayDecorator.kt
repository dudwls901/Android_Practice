package fastcampus.practice.callendarcustom

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class OneDayDecorator(private val date: CalendarDay = CalendarDay.today()) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay?): Boolean  = date != null && day == date

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(object : StyleSpan(Typeface.BOLD){})
        view?.addSpan(object : RelativeSizeSpan(1.4f){})
        view?.addSpan(object : ForegroundColorSpan(Color.GREEN){})
//        view?.addSpan(object : )
    }
}

