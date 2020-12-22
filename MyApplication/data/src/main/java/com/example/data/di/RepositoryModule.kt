package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.BooksRepositoryImpl
import com.example.data.database.BookDatabase
import com.example.data.service.BooksService
import com.example.domain.repository.BooksRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@ExperimentalSerializationApi
class RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(booksService: BooksService, db: BookDatabase): BooksRepository =
        BooksRepositoryImpl(booksService, db)

    @Provides
    @Singleton
    fun provideBookDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, BookDatabase::class.java, "book_db").build()

    @Provides
    @Singleton
    fun provideBooksApi(retrofit: Retrofit): BooksService =
        retrofit.create(BooksService::class.java)

    @Provides
    fun provideBaseUrl(): String = "https://api.itbook.store/1.0/"

    @Provides
    fun provideContentType() = "application/json".toMediaType()


    @Provides
    @Singleton
    fun provideRetrofit2(
        okHttpClient: OkHttpClient,
        baseUrl: String,
        contentType: MediaType
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(contentType))
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .readTimeout(100, TimeUnit.SECONDS)
            .connectTimeout(100, TimeUnit.SECONDS)
            .build()
    }


}