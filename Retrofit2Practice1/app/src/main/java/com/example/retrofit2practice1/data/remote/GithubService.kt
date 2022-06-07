package com.example.retrofit2practice1.data.remote

import com.example.retrofit2practice1.data.remote.GithubRepositoryData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("users/{username}/repos")
    fun getRepoList(@Path("username") name: String) : Call<List<GithubRepositoryData>>
}