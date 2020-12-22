package com.example.domain.usecase

import com.example.domain.Book
import com.example.domain.repository.BooksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class BooksUseCase @Inject constructor(private val booksRepository: BooksRepository) {
    suspend fun searchBook(bookName: String): Flow<List<Book>> {
        return booksRepository.searchBook(bookName).onStart {
            this.emit(
                mutableListOf(
                    Book("앙 스켈레톤", "$0.0", "", ""),
                    Book("앙 스켈레톤", "$0.0", "", "")
                )
            )
        }
    }

}