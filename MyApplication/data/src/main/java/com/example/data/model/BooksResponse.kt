package com.example.data.model

import com.example.domain.Book

data class BooksResponse(
    val error: String,
    val total: String,
    val page: String,
    val books: List<Book>
)
