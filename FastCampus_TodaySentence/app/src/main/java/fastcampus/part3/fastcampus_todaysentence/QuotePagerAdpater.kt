package fastcampus.part3.fastcampus_todaysentence

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fastcampus.part3.fastcampus_todaysentence.databinding.ItemQuoteBinding

class QuotePagerAdpater(
    private val quotes: List<Quote>,
    private val isNameRevealed: Boolean
) : RecyclerView.Adapter<QuotePagerAdpater.QuoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder =
        QuoteViewHolder(
            ItemQuoteBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        )


    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val actualPosition = position%quotes.size
        holder.bind(quotes[actualPosition],isNameRevealed)
    }
    //무한 스크롤(페이징)의 무난한 방식
    //getItemCount에 큰값을 줘서 많은 값이     있다고 어댑터 속이기
    //결국에는 끝이 있긴 함(Int.MAX_VALUE)
    override fun getItemCount() = Int.MAX_VALUE

    class QuoteViewHolder(private val binding: ItemQuoteBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(quote:Quote, isNameRevealed: Boolean){
             binding.quoteTextView.text = "\"${quote.quote}\""
            if(isNameRevealed){
                binding.nameTextView.text = "- ${quote.name}"
                //리사이클러뷰이기 때문에 재사용할 때 visibility 제대로 처리 안 하면 안 보일 때도 있음
                binding.nameTextView.visibility = View.VISIBLE
            }else{
                binding.nameTextView.visibility = View.GONE
            }

        }
    }
}