package com.clean.check_app.di

import com.clean.domain.repository.MainRepository
import com.clean.domain.repository.SplashRepository
import com.clean.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideCheckLoveCalculatorUseCases(repository: MainRepository) =
        CheckLoveCalculatorUseCase(repository)

    @Provides
    @Singleton
    fun provideCheckAppVersionUseCases(repository: SplashRepository) =
        CheckAppVersionUseCase(repository)

    @Provides
    @Singleton
    fun provideGetStatisticsUseCase(repository: MainRepository) =
        GetStatisticsUseCase(repository)

    @Provides
    @Singleton
    fun provideSetStatisticsUseCase(repository: MainRepository) =
        SetStatisticsUseCase(repository)


    @Provides
    @Singleton
    fun provideSetScoreUseCase(repository: MainRepository) =
        SetScoreUseCase(repository)

    @Provides
    @Singleton
    fun provideGetScoreUseCase(repository: MainRepository) =
        GetScoreUseCase(repository)
}