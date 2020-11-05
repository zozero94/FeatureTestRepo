package com.example.myapplication.ui.kasper

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.myapplication.R.layout
import com.example.myapplication.databinding.ActivityKasperBinding
import com.example.myapplication.ui.base.BaseActivity

class KasperActivity : BaseActivity<ActivityKasperBinding>() {
    override val layoutId: Int = layout.activity_kasper

    private val kasperViewModel by viewModels<KasperViewModel>()

    private var kasperTooltipView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = kasperViewModel

        kasperViewModel.showKasperTooltipIfNeed()
        kasperViewModel.isShowToolTip().observe(this, { isShow ->
            if (isShow) {
                kasperTooltipView = binding.kasperToolTip.viewStub?.inflate()
            } else {
                kasperTooltipView?.visibility = View.GONE
            }
        })
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, KasperActivity::class.java)
    }
}