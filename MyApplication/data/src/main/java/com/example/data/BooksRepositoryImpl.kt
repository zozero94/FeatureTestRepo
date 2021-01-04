package com.example.data

import com.example.data.database.BookDao
import com.example.data.database.entity.BookEntity
import com.example.data.model.BooksApiResponse
import com.example.data.service.BooksService
import com.example.domain.Book
import com.example.domain.repository.BooksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BooksRepositoryImpl @Inject constructor(
    private val booksService: BooksService,
    private val dao: BookDao
) : BooksRepository {

    override suspend fun searchBookAndUpdateCache(bookName: String): Flow<List<Book>> {
        return flow {
            val localBook = getLocalBook(bookName)
            val cachedBook = localBook.map { it.mapToBook() }
            emit(cachedBook)

            val remoteBook = getRemoteBook(bookName)
            val newBooks = remoteBook.map { it.mapToBookEntity() }
            dao.insertBooks(newBooks)

            val updatedBook = newBooks.map { it.mapToBook() }
            emit(updatedBook)
        }
    }

    private suspend fun getLocalBook(bookName: String): List<BookEntity> {
        return dao.getBooks(bookName)
    }

    private suspend fun getRemoteBook(bookName: String): List<BooksApiResponse.BookResponse> {
        return booksService.searchBook(bookName).books
    }


}
