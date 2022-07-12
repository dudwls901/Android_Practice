package com.clean.check_app.di

import com.clean.domain.repository.MainRepository
import com.clean.domain.usecase.CheckLoveCalculatorUseCase
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
}