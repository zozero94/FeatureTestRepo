package com.example.myapplication.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

    abstract val layoutId: Int
    protected lateinit var binding: B


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(
            layoutInflater,
            layoutId, null, false
        )
        binding.lifecycleOwner = this
        setContentView(binding.root)
    }


}

