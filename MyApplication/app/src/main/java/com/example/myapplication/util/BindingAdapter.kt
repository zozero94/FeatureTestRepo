package com.example.myapplication.util

import androidx.annotation.MainThread
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ui.main.MainAdapter
import com.example.myapplication.ui.main.TestType

@BindingAdapter(value = ["replaceMainContent"])
@MainThread
fun RecyclerView.replaceMainContent(list: List<TestType>?) {
    if (list != null) {
        (adapter as MainAdapter).replaceItems(list)
    }
}