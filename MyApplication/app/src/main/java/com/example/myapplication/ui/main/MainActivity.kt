package com.example.myapplication.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import com.example.myapplication.R.layout
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutId: Int = layout.activity_main

    private val mainViewModel by viewModels<MainViewModel>()

    private val adapter by lazy {
        MainAdapter { type ->
            when (type) {
                is TestType.KasperTest -> {
                    startActivity(type.intent)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = mainViewModel
        binding.mainContent.adapter = adapter

        mainViewModel.getItemList().observe(this, {
            adapter.replaceItems(it)
        })
    }


}

