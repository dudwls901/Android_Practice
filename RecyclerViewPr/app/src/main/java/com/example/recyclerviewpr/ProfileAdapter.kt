package com.example.recyclerviewpr

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.recyclerviewpr.databinding.ItemProfileBinding
import java.util.*

class ProfileAdapter(val onProfileClicked: (Profile) -> Unit) : ListAdapter<Profile, MainViewHolders.ProfileViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolders.ProfileViewHolder {
        Log.d(TAG, "onCreateViewHolder: ")
        return MainViewHolders.ProfileViewHolder(
            ItemProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolders.ProfileViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: ")
        holder.bind(currentList[position])
        holder.itemView.setOnClickListener {
            onProfileClicked(currentList[position])
        }
    }

    fun moveItem(fromPosition: Int, toPosition: Int){
        currentList.toMutableList().apply {
            Collections.swap(this, fromPosition, toPosition)
            submitList(this)
        }
    }

    fun removeItem(position: Int){
        currentList.toMutableList().apply {
            removeAt(position)
            submitList(this)
        }
    }

    companion object {
        @JvmStatic
        val TAG = ProfileAdapter::class.java.simpleName

        val diffUtil = object : DiffUtil.ItemCallback<Profile>() {
            override fun areItemsTheSame(oldItem: Profile, newItem: Profile) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Profile, newItem: Profile) =
                oldItem.hashCode() == newItem.hashCode()
        }
    }
}