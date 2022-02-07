package fastcampus.practice.fastcampus_githubrepo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import fastcampus.practice.fastcampus_githubrepo.data.entity.GithubRepoEntity
import fastcampus.practice.fastcampus_githubrepo.databinding.ViewholderRepositoryItemBinding
import fastcampus.practice.fastcampus_githubrepo.extensions.loadCenterInside

class RepositoryRecyclerAdapter : RecyclerView.Adapter<RepositoryRecyclerAdapter.RepositoryItemViewHolder>() {

    private var repositoryList: List<GithubRepoEntity> = listOf()
    private lateinit var repositoryClickListener: (GithubRepoEntity) -> Unit

    inner class RepositoryItemViewHolder(
        private val binding: ViewholderRepositoryItemBinding,
        val searchResultClickListener: (GithubRepoEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: GithubRepoEntity) = with(binding) {
            ownerProfileImageView.loadCenterInside(data.owner.avatarUrl, 24f)
            ownerNameTextView.text = data.owner.login
            nameTextView.text = data.fullName
            subtextTextView.text = data.description
            stargazersCountText.text = data.stargazersCount.toString()
            data.language?.let { language ->
                languageText.isGone = false
                languageText.text = language
            } ?: kotlin.run {
                languageText.isGone = true
                languageText.text = ""
            }
        }

        fun bindViews(data: GithubRepoEntity) {
            binding.root.setOnClickListener {
                searchResultClickListener(data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryItemViewHolder {
        val view = ViewholderRepositoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryItemViewHolder(view, repositoryClickListener)
    }

    override fun onBindViewHolder(holder: RepositoryItemViewHolder, position: Int) {
        holder.bindData(repositoryList[position])
        holder.bindViews(repositoryList[position])
    }

    override fun getItemCount(): Int = repositoryList.size

    fun setRepositoryList(searchResultList: List<GithubRepoEntity>, searchResultClickListener: (GithubRepoEntity) -> Unit) {
        this.repositoryList = searchResultList
        this.repositoryClickListener = searchResultClickListener
        notifyDataSetChanged()
    }
}