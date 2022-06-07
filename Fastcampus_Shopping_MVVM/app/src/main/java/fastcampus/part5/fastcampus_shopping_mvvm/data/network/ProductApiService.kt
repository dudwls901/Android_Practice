package fastcampus.part5.fastcampus_shopping_mvvm.data.network

import fastcampus.part5.fastcampus_shopping_mvvm.data.response.ProductResponse
import fastcampus.part5.fastcampus_shopping_mvvm.data.response.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): Response<ProductsResponse>

    @GET("products/{productId}")
    suspend fun getProduct(@Path("productId") productId: Long): Response<ProductResponse>
}