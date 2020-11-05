package com.example.myapplication.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.myapplication.R.layout
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutId: Int = layout.activity_main

    private val mainViewModel by viewModels<MainViewModel>()

    private var kasperTooltipView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = mainViewModel

        mainViewModel.showKasperTooltipIfNeed()
        mainViewModel.isShowToolTip().observe(this, { isShow ->
            if (isShow) {
                kasperTooltipView = binding.kasperToolTip.viewStub?.inflate()
            } else {
                kasperTooltipView?.visibility = View.GONE
            }
        })

    }


}