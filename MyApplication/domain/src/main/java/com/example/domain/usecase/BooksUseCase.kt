package com.example.domain.usecase

import com.example.domain.Book
import com.example.domain.repository.BooksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class BooksUseCase @Inject constructor(private val booksRepository: BooksRepository) {
    suspend fun searchBook(bookName: String): Flow<List<Book>> {
        return booksRepository.searchBook(bookName).onStart {
            val skeletonList = (0..10).map { SKELETON }
            this.emit(skeletonList)
        }
    }

    companion object {
        private val SKELETON =
            Book("앙 스켈레톤", "스켈레톤 데이터", "$0.0", "https://itbook.store/img/books/9781430265740.png")
    }
}