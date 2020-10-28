package com.example.myapplication.ui

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakewharton.rxrelay3.BehaviorRelay
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {
    private val _showToolTip = MutableLiveData<Boolean>()
    fun isShowToolTip(): LiveData<Boolean> = _showToolTip


    private val inputTextRelay = BehaviorRelay.create<CharSequence>()
    val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            inputTextRelay.accept(s)
        }

    }

    private val _result = MutableLiveData<CharSequence>()
    fun getResult() = _result

    init {
        inputTextRelay.observeOn(Schedulers.computation())
            .throttleLast(100, TimeUnit.MILLISECONDS)
            .subscribe {
                _result.postValue(it)
            }
    }

    fun showKasperTooltipIfNeed() {
        viewModelScope.launch {
            _showToolTip.value = true
            delay(3000)
            _showToolTip.value = false
        }
    }
}