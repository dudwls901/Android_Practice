package fastcampus.practice.callendarcustom.second.view

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.prolificinteractive.materialcalendarview.CalendarDay
import fastcampus.practice.callendarcustom.second.model.SelectedDate

object DateListBindAdapter {
    @BindingAdapter("items")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, items: LiveData<List<CalendarDay>>){
        Log.e("Hhhhhhh",items.value.toString())
        if(recyclerView.adapter==null){
            val adapter = DateListAdapter()
            adapter.setHasStableIds(true)
            recyclerView.adapter = adapter
        }
        val dateListAdapter = recyclerView.adapter as DateListAdapter
        dateListAdapter.submitList(items.value)
    }
}