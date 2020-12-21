package com.example.myapplication.ui.books

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.domain.usecase.BooksUseCase


class BooksViewModel @ViewModelInject constructor(val booksUseCase: BooksUseCase) : ViewModel() {

}