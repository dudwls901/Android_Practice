package com.example.retrofit2practice1.data.remote

import com.google.gson.annotations.SerializedName

data class GithubRepositoryData(
    @SerializedName("name")
    val repoName : String,
    @SerializedName("id")
    val repoId : Int,
    val private : Boolean
)
