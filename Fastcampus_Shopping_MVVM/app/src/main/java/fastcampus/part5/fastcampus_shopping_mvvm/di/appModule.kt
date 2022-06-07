package fastcampus.part5.fastcampus_shopping_mvvm.di

import fastcampus.part5.fastcampus_shopping_mvvm.data.network.buildOkHttpClient
import fastcampus.part5.fastcampus_shopping_mvvm.data.network.provideGsonConverterFactory
import fastcampus.part5.fastcampus_shopping_mvvm.data.network.provideProductApiService
import fastcampus.part5.fastcampus_shopping_mvvm.data.network.provideProductRetrofit
import fastcampus.part5.fastcampus_shopping_mvvm.data.repository.DefaultProductRepository
import fastcampus.part5.fastcampus_shopping_mvvm.data.repository.ProductRepository
import fastcampus.part5.fastcampus_shopping_mvvm.domain.GetProductItemUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val appModule = module {

    //Coroutines Dispatcher
    single { Dispatchers.Main }
    single { Dispatchers.IO }

    //UseCases
    factory { GetProductItemUseCase(get()) }

    //Repositories
    single<ProductRepository> {DefaultProductRepository(get(), get())}

    single { provideGsonConverterFactory() }

    single { buildOkHttpClient() }

    single { provideProductRetrofit(get(), get()) }

    single { provideProductApiService(get()) }

}