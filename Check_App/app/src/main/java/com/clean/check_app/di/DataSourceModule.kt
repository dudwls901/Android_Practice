package com.clean.check_app.di

import com.clean.data.remote.api.LoveCalculatorApi
import com.clean.data.repository.remote.datasource.MainDataSource
import com.clean.data.repository.remote.datasource.SplashDataSource
import com.clean.data.repository.remote.datasourceimpl.MainDataSourceImpl
import com.clean.data.repository.remote.datasourceimpl.SplashDataSourceImpl
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    @Singleton
    fun provideMainDataSource(
        loveCalculatorApi: LoveCalculatorApi,
    ) : MainDataSource{
        return MainDataSourceImpl(
            loveCalculatorApi
        )
    }

    @Provides
    @Singleton
    fun provideSplashDataSource(
        firebaseRtDb : FirebaseDatabase,
        fireStore : FirebaseFirestore
    ) : SplashDataSource{
        return SplashDataSourceImpl(
            firebaseRtDb, fireStore
        )
    }
}