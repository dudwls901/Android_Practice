package fastcampus.part3.fastcampus_bookreview.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fastcampus.part3.fastcampus_bookreview.databinding.ItemBookBinding
import fastcampus.part3.fastcampus_bookreview.model.Book

class BookAdapter(private val itemClickedListener: (Book) -> Unit): ListAdapter<Book, BookAdapter.BookItemViewHolder>(diffUtil) {
    //미리 만들어진 몇 개의 뷰들 = 뷰 홀더
    //미리 만들어진 뷰홀더가 없을 시 새로 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        return BookItemViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }
    //실제로 뷰 홀더가 뷰에 그려지게 됐을 때 데이터를 바인드하게 되는 함수
    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class BookItemViewHolder(private val binding: ItemBookBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(bookModel: Book){
            binding.titleTextView.text = bookModel.title
            binding.descriptionTextView.text = bookModel.description

            binding.root.setOnClickListener{
                itemClickedListener(bookModel)
            }

            //서버에서 받아온 url을 통해 이미지뷰에 이미지 띄우기
            Glide
                .with(binding.coverImageView.context)
                .load(bookModel.coverSmallUrl)
                .into(binding.coverImageView)
        }
    }

    //diffUtil
    //리사이클러뷰가 실제로 뷰의 포지션이 변경돼었을 때 새로운 값을 할당할지 말지 결정
    //같은 아이템이 올라오면 굳이 이미 값이 할당되어있기 때문에 똑같은 값을 다시 할당할 필요가 없음
    //이를 판단/결정해주는 것이 diffUtil

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<Book>(){
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}