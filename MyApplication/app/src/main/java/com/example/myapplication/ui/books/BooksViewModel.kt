package com.example.myapplication.ui.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.Book
import com.example.domain.usecase.BooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BooksViewModel @Inject constructor(private val booksUseCase: BooksUseCase) :
    ViewModel() {
    private val _books = MutableLiveData<List<Book>>()
    fun bindBooks(): LiveData<List<Book>> = _books

    fun searchBook(bookName: String) {
        viewModelScope.launch {
            booksUseCase.searchBook(bookName).collect {
                _books.value = it
            }
        }
    }
}