package fastcampus.part5.fastcampus_shopping_mvvm.data.response

import fastcampus.part5.fastcampus_shopping_mvvm.data.entity.product.ProductEntity
import java.util.*

data class ProductResponse(
    val id: Long,
    val createdAt: Long,
    val productName: String,
    val productPrice: Int,
    val productImage: String,
    val productType: String,
    val productIntroductionImage: String
){
    fun toEntity(): ProductEntity =
        ProductEntity(
            id = id.toLong(),
            createdAt = Date(createdAt),
            productName = productName,
            productPrice = productPrice.toDouble().toInt(),
            productImage = productImage,
            productType = productType,
            productIntroductionImage = productIntroductionImage
        )
}
