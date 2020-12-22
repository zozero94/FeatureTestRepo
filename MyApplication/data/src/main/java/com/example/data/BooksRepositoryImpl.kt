package com.example.data

import com.example.data.database.BookDatabase
import com.example.data.database.entity.BookEntity
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
    db: BookDatabase
) : BooksRepository {

    private val bookDao = db.bookDao()

    override suspend fun searchBook(bookName: String): Flow<List<Book>> {
        return flow {
            val localBook = bookDao.getBooks().map {
                Book(it.title, it.price, it.image)
            }
            emit(localBook)

            val remoteBook = booksService.searchBook(bookName).books.asSequence().map {
                BookEntity(it.title, it.subTitle, it.price, it.image, it.url)
            }.also {
                bookDao.insertBooks(it.toList())
            }.map {
                Book(it.title, it.price, it.image)
            }

            emit(remoteBook.toList())

        }
    }


}
