package com.example.myapplication.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.inflate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
            MainViewModel::class.java
        )
    }

    private var kasperTooltipView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = inflate(
            layoutInflater,
            R.layout.activity_main, null, false
        )
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        viewModel.showKasperTooltipIfNeed()
        viewModel.isShowToolTip().observe(this, Observer { isShow ->
            if (isShow) {
                kasperTooltipView = binding.kasperToolTip.viewStub?.inflate()
            } else {
                kasperTooltipView?.visibility = View.GONE
            }
        })

    }


}