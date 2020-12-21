package com.example.data.di

import com.example.data.BooksRepositoryImpl
import com.example.domain.repository.BooksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class RepositoryModule {
    @Provides
    fun provideRepository(): BooksRepository = BooksRepositoryImpl()
}