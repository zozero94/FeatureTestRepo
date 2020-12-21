package com.example.data

import com.example.data.service.BooksService
import com.example.domain.Book
import com.example.domain.repository.BooksRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BooksRepositoryImpl @Inject constructor(private val booksService: BooksService) :
    BooksRepository {
    override suspend fun searchBook(bookName: String): List<Book> =
        booksService.searchBook(bookName).books.map {
            Book(it.title, it.price, it.image)
        }

}