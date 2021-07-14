package com.example.myapplication.ui.dictation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DictationViewModel @Inject constructor() : ViewModel() {
    private val mainText = MutableLiveData<String>()
    fun getDictationText() = mainText

    fun setText(text: String) {
        mainText.value = text
    }
}