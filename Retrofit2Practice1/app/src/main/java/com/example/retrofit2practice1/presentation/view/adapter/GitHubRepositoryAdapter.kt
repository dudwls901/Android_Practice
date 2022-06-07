package com.example.retrofit2practice1.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit2practice1.data.remote.GithubRepositoryData
import com.example.retrofit2practice1.databinding.ItemGithubBinding


class GitHubRepositoryAdapter : ListAdapter<GithubRepositoryData, GitHubRepositoryAdapter.ViewHolder>(
    diffUtil
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemGithubBinding.inflate(LayoutInflater.from(parent.context),parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ViewHolder(private val binding : ItemGithubBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(githubRepositoryData: GithubRepositoryData){
            Thread {
                binding.githubRepositoryData = githubRepositoryData
            }.start()
        }
    }

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<GithubRepositoryData>() {
            override fun areItemsTheSame(
                oldItem: GithubRepositoryData,
                newItem: GithubRepositoryData
            ): Boolean {
                return oldItem ==newItem
            }

            override fun areContentsTheSame(
                oldItem: GithubRepositoryData,
                newItem: GithubRepositoryData
            ): Boolean {
                return oldItem.repoId == newItem.repoId
            }
        }
    }

}