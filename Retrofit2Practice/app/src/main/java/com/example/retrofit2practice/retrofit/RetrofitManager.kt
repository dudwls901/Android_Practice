package com.example.retrofit2practice.retrofit

import android.util.Log
import com.example.retrofit2practice.Utils.API
import com.example.retrofit2practice.Utils.Constants.TAG
import com.example.retrofit2practice.Utils.RESPONSE_STATE
import com.example.retrofit2practice.retrofit.IRetrofit
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {

    companion object{
        val instance = RetrofitManager()
    }

    //http 콜 만들기
    //레트로핏 인터페이스 가져오기
    private val iRetrofit : IRetrofit? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    //사진 검색 api 호출
    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE,String) -> Unit){
        //searchTerm이 비어있으면 it를 term에 넣고 없으면 ""
//        val term = searchTerm.let{it}?: ""
        //searchTerm이 비어있으면 searchTerm 그대로 넣고 없으면 ""
        val term = searchTerm?:""
        val call = iRetrofit?.searchPhotos(searchTerm = term).let{
            it
        }?: return

//        val call = iRetrofit?.searchPhotos(searchTerm = term) ?: return //same

        call.enqueue(object : retrofit2.Callback<JsonElement>{
            //응답 성공시
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "onResponse(): called / response : ${response.raw()}")
                completion(RESPONSE_STATE.OKAY,response.body().toString())
            }

            //응답 실패시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "RetrofitManager - onFailure(): called /t : $t")
                completion(RESPONSE_STATE.FAIL,t.toString())
            }

        })

    }
}