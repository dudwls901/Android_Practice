package fastcampus.part3.fastcampus_tinder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fastcampus.part3.fastcampus_tinder.databinding.ItemCardBinding
import fastcampus.part3.fastcampus_tinder.databinding.ItemMatchedUserBinding
import fastcampus.part3.fastcampus_tinder.model.CardItem

class MatchedUserAdapter : ListAdapter<CardItem, MatchedUserAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemMatchedUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ViewHolder(private val binding: ItemMatchedUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cardItem: CardItem) {
            binding.userNameTextView.text = cardItem.name
        }
    }

    companion object {
        val diffUtil by lazy {
            object : DiffUtil.ItemCallback<CardItem>() {
                override fun areItemsTheSame(oldItem: CardItem, newItem: CardItem) =
                    oldItem.userId == newItem.userId

                override fun areContentsTheSame(oldItem: CardItem, newItem: CardItem) =
                    oldItem == newItem
            }
        }
    }
}