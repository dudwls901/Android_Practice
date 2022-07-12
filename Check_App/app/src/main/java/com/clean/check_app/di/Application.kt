package com.clean.check_app.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application() {
    companion object{
        private lateinit var application: Application
        fun getInstance() : Application = application
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }
}