package com.example.myapplication.util

import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter

object DataBindingUtil {
    @JvmStatic
    @BindingAdapter("addTextWatcher")
    fun addTextWatcher(view: EditText, watcher: TextWatcher) {
        view.addTextChangedListener(watcher);
    }
}
