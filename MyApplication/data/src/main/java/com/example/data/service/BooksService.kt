package com.example.data.service

import com.example.data.model.BooksResponse
import retrofit2.http.GET
import retrofit2.http.Path


interface BooksService {
    @GET("https://api.itbook.store/1.0/search/{bookName}}")
    suspend fun searchBook(@Path("bookName") bookName: String): BooksResponse

}