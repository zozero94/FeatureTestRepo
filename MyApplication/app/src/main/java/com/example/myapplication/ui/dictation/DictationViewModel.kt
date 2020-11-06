package com.example.myapplication.ui.dictation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DictationViewModel @ViewModelInject constructor() : ViewModel() {
    private val mainText = MutableLiveData<String>()
    fun getDictationText() = mainText

    fun setText(text: String) {
        mainText.value = text
    }
}