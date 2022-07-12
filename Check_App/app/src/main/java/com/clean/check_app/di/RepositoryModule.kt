package com.clean.check_app.di

import com.clean.data.repository.MainRepositoryImpl
import com.clean.data.repository.remote.datasource.MainDataSource
import com.clean.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMainRepository(
        mainDataSource: MainDataSource
    ): MainRepository {

    return MainRepositoryImpl(
        mainDataSource
    )
    }
}