package com.example.domain.repository

import com.example.domain.Book

interface BooksRepository {
    suspend fun searchBook(bookName: String): List<Book>
}