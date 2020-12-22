package com.example.domain.repository

import com.example.domain.Book
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    suspend fun searchBook(bookName: String): Flow<List<Book>>
}