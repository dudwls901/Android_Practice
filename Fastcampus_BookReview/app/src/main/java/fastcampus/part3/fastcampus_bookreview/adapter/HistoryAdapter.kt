package fastcampus.part3.fastcampus_bookreview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fastcampus.part3.fastcampus_bookreview.databinding.ItemBookBinding
import fastcampus.part3.fastcampus_bookreview.databinding.ItemHistoryBinding
import fastcampus.part3.fastcampus_bookreview.model.Book
import fastcampus.part3.fastcampus_bookreview.model.History

//Delete 버튼에 대한 이벤트는 메인 액티비티에서 함수 던져서
//실제로 x버튼의 클릭 리스너가 발생할 때 그 함수를 호출하는 방법
class HistoryAdapter(val historyDeleteClickedListener: (String) -> Unit, val historyClickedListener: (String) -> Unit) : ListAdapter<History, HistoryAdapter.HistoryItemViewHolder>(diffUtil) {
    //미리 만들어진 몇 개의 뷰들 = 뷰 홀더
    //미리 만들어진 뷰홀더가 없을 시 새로 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder {
        return HistoryItemViewHolder(ItemHistoryBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }
    //실제로 뷰 홀더가 뷰에 그려지게 됐을 때 데이터를 바인드하게 되는 함수
    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class HistoryItemViewHolder(private val binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(historyModel: History){
            binding.historyKeywordTextView.text = historyModel.keyword
            binding.historyKeywordDeleteButton.setOnClickListener {
                historyClickedListener(historyModel.keyword.orEmpty())
            }
            binding.historyKeywordTextView.setOnClickListener {
                historyClickedListener(historyModel.keyword.orEmpty())
            }
        }
        //todo 최근 검색어 누르면 바로 검색
    }

    //diffUtil
    //리사이클러뷰가 실제로 뷰의 포지션이 변경돼었을 때 새로운 값을 할당할지 말지 결정
    //같은 아이템이 올라오면 굳이 이미 값이 할당되어있기 때문에 똑같은 값을 다시 할당할 필요가 없음
    //이를 판단/결정해주는 것이 diffUtil

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<History>(){
            override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem.keyword == newItem.keyword
            }
        }
    }

}