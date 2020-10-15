package com.example.myapplication

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay3.BehaviorRelay
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {
    private val inputTextRelay = BehaviorRelay.create<CharSequence>()
    val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            Log.e("zero",s.toString())
            inputTextRelay.accept(s)
        }

    }

    private val _result = MutableLiveData<CharSequence>()
    fun getResult() = _result

    init {
        inputTextRelay.observeOn(Schedulers.computation())
            .throttleLast(100, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribe {
                _result.postValue(it)
            }
    }
}