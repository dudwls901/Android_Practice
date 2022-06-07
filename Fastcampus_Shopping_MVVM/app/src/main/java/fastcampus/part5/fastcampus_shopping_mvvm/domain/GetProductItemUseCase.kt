package fastcampus.part5.fastcampus_shopping_mvvm.domain

import fastcampus.part5.fastcampus_shopping_mvvm.data.entity.product.ProductEntity
import fastcampus.part5.fastcampus_shopping_mvvm.data.repository.ProductRepository

internal class GetProductItemUseCase(
    private val productRepository: ProductRepository
): UseCase {

    suspend operator fun invoke(productId: Long): ProductEntity? {
        return productRepository.getProductItem(productId)
    }

}