package com.example.myapplication.ui.books

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.Book
import com.example.domain.usecase.BooksUseCase
import kotlinx.coroutines.launch


class BooksViewModel @ViewModelInject constructor(private val booksUseCase: BooksUseCase) :
    ViewModel() {
    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    fun searchBook(bookName: String) {
        viewModelScope.launch {
            _books.value = booksUseCase.searchBook(bookName)
            Log.d("zero", books.value.toString())
        }
    }
}