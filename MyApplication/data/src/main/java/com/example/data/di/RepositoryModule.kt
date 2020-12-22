package com.example.data.di

import com.example.data.BooksRepositoryImpl
import com.example.data.database.BookDao
import com.example.data.service.BooksService
import com.example.domain.repository.BooksRepository
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
    fun provideRepository(booksService: BooksService, db: BookDao): BooksRepository =
        BooksRepositoryImpl(booksService, db)

}