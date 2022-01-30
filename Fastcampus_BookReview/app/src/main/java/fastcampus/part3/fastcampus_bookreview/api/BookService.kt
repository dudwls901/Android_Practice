package fastcampus.part3.fastcampus_bookreview.api

import fastcampus.part3.fastcampus_bookreview.model.BestSellerDTO
import fastcampus.part3.fastcampus_bookreview.model.SearchBookDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {
    @GET("/api/search.api?&output=json")
    fun getBooksByName(
        @Query("key") apiKey: String,
        @Query("query") keyWord: String
    ): Call<SearchBookDTO>

    @GET("/api/bestSeller.api?&output=json&categoryId=100")
    fun getBestSellerBooks(
        @Query("key") apiKey: String
    ): Call<BestSellerDTO>
}