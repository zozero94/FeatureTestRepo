package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.database.BookDao
import com.example.data.database.BookDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@ExperimentalSerializationApi
class DatabaseModule {
    @Provides
    @Singleton
    fun provideBookDao(db: BookDatabase): BookDao = db.bookDao()

    @Provides
    @Singleton
    fun provideBookDatabase(@ApplicationContext context: Context): BookDatabase =
        Room.databaseBuilder(context, BookDatabase::class.java, "book").build()
}