package com.example.myapplication.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.inflate
import androidx.lifecycle.Observer
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()
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