package fastcampus.practice.callendarcustom

import android.app.Activity
import android.graphics.drawable.Drawable
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class MySelectorDecorator(context: Activity) : DayViewDecorator {
    //drawable 가져오기 패캠에서 찾자
    private val drawable: Drawable = context.resources.getDrawable(R.drawable.my_selector)
    override fun shouldDecorate(day: CalendarDay): Boolean {
        return true
    }

    override fun decorate(view: DayViewFacade) {
        view.setSelectionDrawable(drawable)
    }

}