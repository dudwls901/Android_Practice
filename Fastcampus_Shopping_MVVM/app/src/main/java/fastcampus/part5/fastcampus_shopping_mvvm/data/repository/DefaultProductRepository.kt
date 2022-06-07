package fastcampus.part5.fastcampus_shopping_mvvm.data.repository

import fastcampus.part5.fastcampus_shopping_mvvm.data.entity.product.ProductEntity
import fastcampus.part5.fastcampus_shopping_mvvm.data.network.ProductApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

//주입
class DefaultProductRepository(
    private val productApi: ProductApiService,
    private val ioDispatcher: CoroutineDispatcher
) : ProductRepository {
    override suspend fun getProductList(): List<ProductEntity> = withContext(ioDispatcher) {
        val response = productApi.getProducts()
        return@withContext if (response.isSuccessful) {
            response.body()?.items?.map { it.toEntity() } ?: listOf()
        } else {
            listOf()
        }
    }

    override suspend fun getLocalProductList(): List<ProductEntity> = withContext(ioDispatcher) {
        productDao.getAll()
    }

    override suspend fun insertProductItem(ProductItem: ProductEntity): Long = withContext(ioDispatcher) {
        productDao.insert(ProductItem)
    }

    override suspend fun insertProductList(ProductList: List<ProductEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun updateProductItem(ProductItem: ProductEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getProductItem(itemId: Long): ProductEntity? = withContext(ioDispatcher) {
        val response = productApi.getProduct(itemId)
        return@withContext if (response.isSuccessful) {
            response.body()?.toEntity()
        } else {
            null
        }
    }

    override suspend fun deleteAll() = withContext(ioDispatcher) {
//        productDao.deleteAll()
    }

    override suspend fun deleteProductItem(id: Long) {
        TODO("Not yet implemented")
    }


}