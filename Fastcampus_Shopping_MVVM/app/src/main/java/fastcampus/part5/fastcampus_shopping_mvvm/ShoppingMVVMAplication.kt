package fastcampus.part5.fastcampus_shopping_mvvm

import android.app.Application
import fastcampus.part5.fastcampus_shopping_mvvm.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ShoppingMVVMAplication : Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger(Level.ERROR)
            androidContext(this@ShoppingMVVMAplication)
            modules(appModule)
        }
    }
}