package com.example.domain.usecase

import com.example.domain.repository.BooksRepository
import javax.inject.Inject

class BooksUseCase @Inject constructor(private val booksRepository: BooksRepository) {
    suspend fun searchBook(bookName: String) = booksRepository.searchBook(bookName)

}