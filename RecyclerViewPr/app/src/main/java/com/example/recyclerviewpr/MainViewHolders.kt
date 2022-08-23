package com.example.recyclerviewpr

import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpr.databinding.ItemFooterBinding
import com.example.recyclerviewpr.databinding.ItemHeaderBinding
import com.example.recyclerviewpr.databinding.ItemProfileBinding

sealed class MainViewHolders {
    class HeaderViewHolder(private val binding: ItemHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(header: String) {
            binding.headerTextView.text = header
        }
    }

    class FooterViewHolder(private val binding: ItemFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(footer: String) {
            binding.footerTextView.text = footer
        }
    }

    class ProfileViewHolder(private val binding: ItemProfileBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(profile: Profile) {
            binding.nameTextView.text = profile.name.toString()
            binding.birthTextView.text = profile.birth.toString()
            binding.profileImageView.setImageResource(R.drawable.ic_launcher_foreground)
        }

        fun setAlpha(alpha: Float){
            with(binding){
                root.alpha = alpha
            }
        }
    }
}
