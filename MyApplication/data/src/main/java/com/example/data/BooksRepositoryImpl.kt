package com.example.data

import com.example.data.database.BookDao
import com.example.data.database.entity.mapToBooks
import com.example.data.model.BooksApiResponse
import com.example.data.model.mapToBookEntities
import com.example.data.service.BooksService
import com.example.domain.Book
import com.example.domain.repository.BooksRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BooksRepositoryImpl @Inject constructor(
    private val booksService: BooksService,
    private val dao: BookDao
) : BooksRepository {

    override suspend fun getBooks(bookName: String): Flow<List<Book>> {
        return dao.getBooks(bookName)
            .map { it.mapToBooks() }
            .stateIn(CoroutineScope(Dispatchers.IO))
            .onStart {
                val remoteBook = getRemoteBook(bookName)
                dao.insertBooks(remoteBook.mapToBookEntities())
            }
    }

    private suspend fun getRemoteBook(bookName: String): List<BooksApiResponse.BookResponse> {
        return booksService.searchBook(bookName).books
    }

}
