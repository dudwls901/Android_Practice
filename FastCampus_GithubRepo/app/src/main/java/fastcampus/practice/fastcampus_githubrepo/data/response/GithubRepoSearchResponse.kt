package fastcampus.practice.fastcampus_githubrepo.data.response

import fastcampus.practice.fastcampus_githubrepo.data.entity.GithubRepoEntity

data class GithubRepoSearchResponse(
    val totalCount: Int,
    val items: List<GithubRepoEntity>
)