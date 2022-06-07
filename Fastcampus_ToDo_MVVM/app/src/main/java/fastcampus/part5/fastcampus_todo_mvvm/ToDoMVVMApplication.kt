package fastcampus.part5.fastcampus_todo_mvvm

import android.app.Application
import fastcampus.part5.fastcampus_todo_mvvm.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ToDoMVVMApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        // Todo Koin Trigger
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@ToDoMVVMApplication)
            modules(appModule)
        }

    }
}