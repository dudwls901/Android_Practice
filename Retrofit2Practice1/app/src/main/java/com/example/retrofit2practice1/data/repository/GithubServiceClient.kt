package com.example.retrofit2practice1.data.repository

import com.example.retrofit2practice1.data.remote.GithubService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object GithubServiceClient {

    private const val BASE_URL ="https://api.github.com/"

    //builder로 레트로핏 구현현

    private var retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var service: GithubService = retrofit.create(GithubService::class.java)

}