package com.example.coroutinetest.di

import com.example.data.repository.RepositoryImpl
import com.example.domain.repository.Repository
import com.example.domain.usecase.ApiUseCase
import com.example.domain.usecase.UseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object AppModule {
    @Provides
    fun provideUseCase(repository: Repository): UseCase = ApiUseCase(repository)
}

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {
    @Provides
    fun provideRepository(): Repository = RepositoryImpl()
}