package fastcampus.practice.callendarcustom.second.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import fastcampus.practice.callendarcustom.second.model.SelectedDate
import java.util.*


class CalendarViewModel : ViewModel() {
    private var _date = MutableLiveData<List<CalendarDay>>()

    val date : LiveData<List<CalendarDay>>
        get() = _date

    fun setDate(list : List<CalendarDay>){
        _date.value = list
    }

}