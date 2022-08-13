package com.clean.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.clean.domain.model.DomainScore
import com.clean.presentation.R
import com.clean.presentation.databinding.ScoreRecyclerItemBinding
import com.clean.presentation.viewmodel.MainViewModel

class ScoreRecyclerViewAdapter (
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<ScoreRecyclerViewAdapter.ScoreRecyclerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ScoreRecyclerItemBinding>(
            layoutInflater,
            R.layout.score_recycler_item,
            parent,
            false
        )
        return ScoreRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScoreRecyclerViewHolder, position: Int) {
        holder.bind(viewModel.scoreList[position])
    }

    override fun getItemCount(): Int {
        return viewModel.scoreList.size
    }

    inner class ScoreRecyclerViewHolder(val binding: ScoreRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: DomainScore){
            binding.data = data
            binding.executePendingBindings()
        }
    }
}