package com.example.myapplication.ui.kasper

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R.layout
import com.example.myapplication.databinding.ActivityKasperBinding
import com.example.myapplication.ui.base.BaseActivity

class KasperActivity : BaseActivity<ActivityKasperBinding>() {
    override val layoutId: Int = layout.activity_kasper

    private val kasperViewModel by viewModels<KasperViewModel>()

    private var kasperTooltipView: View? = null
    private val adapter by lazy { KasperAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = kasperViewModel

        binding.inputTextRv.adapter = adapter
        binding.inputTextRv.layoutManager = LinearLayoutManager(this)

        kasperViewModel.showKasperTooltipIfNeed()
        kasperViewModel.isShowToolTip().observe(this, { isShow ->
            if (isShow) {
                kasperTooltipView = binding.kasperToolTip.viewStub?.inflate()
            } else {
                kasperTooltipView?.visibility = View.GONE
            }
        })
        binding.sendButton.setOnClickListener {
            val curText = binding.editText.text ?: return@setOnClickListener
            if (curText.isEmpty()) {
                binding.editText.setText("/캐스퍼 ")
            } else {
                adapter.insertText(curText.toString())
                curText.clear()
            }
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, KasperActivity::class.java)
    }
}