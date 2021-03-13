package com.example.domain.usecase

import com.example.domain.Book
import com.example.domain.repository.BooksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BooksUseCase @Inject constructor(private val booksRepository: BooksRepository) {
    suspend fun searchBook(bookName: String): Flow<List<Book>> {
        return booksRepository.getBooks(bookName)
    }
}