package fastcampus.practice.callendarcustom.second.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prolificinteractive.materialcalendarview.CalendarDay
import fastcampus.practice.callendarcustom.databinding.DateItemBinding
import fastcampus.practice.callendarcustom.second.model.SelectedDate

class DateListAdapter: ListAdapter<CalendarDay, DateListAdapter.ItemViewHolder>(diffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DateListAdapter.ItemViewHolder = ItemViewHolder(DateItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: DateListAdapter.ItemViewHolder, position: Int) {
        Log.e("rrrrrrrrr",position.toString() +  " " +  currentList[position])
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    inner class ItemViewHolder(private val binding: DateItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(dateModel : CalendarDay){
            binding.selectedDate = dateModel
            Log.e("bindadapterrrrr" , dateModel.toString() + " " + binding.selectedDate)

        }
    }

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<CalendarDay>(){
            override fun areItemsTheSame(oldItem: CalendarDay, newItem: CalendarDay): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CalendarDay, newItem: CalendarDay): Boolean {
                return oldItem == newItem
            }
        }
    }


}