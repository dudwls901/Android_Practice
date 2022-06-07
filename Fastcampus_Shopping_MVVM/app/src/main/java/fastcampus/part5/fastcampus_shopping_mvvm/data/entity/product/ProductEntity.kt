package fastcampus.part5.fastcampus_shopping_mvvm.data.entity.product

import java.util.*

data class ProductEntity(
    val id: Long,
    val createdAt: Date,
    val productName: String,
    val productPrice: Int,
    val productImage: String,
    val productType: String,
    val productIntroductionImage: String
)