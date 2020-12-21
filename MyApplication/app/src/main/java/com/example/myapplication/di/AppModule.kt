package com.example.myapplication.di

import com.example.domain.repository.BooksRepository
import com.example.domain.usecase.BooksUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class AppModule {

    @Provides
    fun provideUseCase(repository: BooksRepository): BooksUseCase = BooksUseCase(repository)
}