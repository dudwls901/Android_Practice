package com.example.retrofit2practice.retrofit

import com.example.retrofit2practice.Utils.API
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IRetrofit {

    //https://www.unsplash.com/search/photos/?query="$searchTerm"
    //Call<JsonElement> : 반환값

    @GET(API.SEARCH_PHOTO)
    fun searchPhotos(@Query("query") searchTerm: String) : Call<JsonElement>  //retrofit call import

    @GET(API.SEARCH_USERS)
    fun searchUsers(@Query("query") searchTerm: String) : Call<JsonElement>
}