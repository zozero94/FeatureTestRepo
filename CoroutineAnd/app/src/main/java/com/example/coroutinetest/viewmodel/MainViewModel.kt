package com.example.coroutinetest.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coroutinetest.data.Item
import com.example.domain.usecase.UseCase

class MainViewModel @ViewModelInject constructor(
    private val useCase: UseCase
) : ViewModel() {

    private val _listItem = MutableLiveData<List<Item>>()
    val listItem: LiveData<List<Item>>
        get() = _listItem

    fun requestApi() {
        useCase.getData()
    }
}