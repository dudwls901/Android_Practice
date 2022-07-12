package com.clean.data.remote.api

import com.clean.data.remote.model.DataLoveResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface LoveCalculatorApi {
    @GET("/getPercentage")
    suspend fun getPercentage(
        @Header("X-RapidAPI-Key") key: String,
        @Header("X-RapidAPI-Host") host: String,
        @Query("fname") fName : String,
        @Query("sname") sName: String,
    ) : Response<DataLoveResponse>
}