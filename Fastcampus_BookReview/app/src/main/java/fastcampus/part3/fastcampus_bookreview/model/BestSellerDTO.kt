package fastcampus.part3.fastcampus_bookreview.model

import com.google.gson.annotations.SerializedName

//전체 api response 받아오기
data class BestSellerDTO(
    @SerializedName("title") val title: String,
    @SerializedName("item") val books: List<Book>
)
