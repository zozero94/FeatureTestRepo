package com.example.myapplication.ui.dictation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.myapplication.R.layout
import com.example.myapplication.databinding.ActivityDictationBinding
import com.example.myapplication.ui.base.BaseActivity
import com.example.myapplication.util.toast

class DictationActivity : BaseActivity<ActivityDictationBinding>() {
    override val layoutId: Int = layout.activity_dictation
    private val dictationViewModel by viewModels<DictationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = dictationViewModel
        binding.textView.setTextSelectListener {
            toast(this, it.toString())
        }
        dictationViewModel.getDictationText().observe(this, {
            binding.textView.setMainContent(it)
        })
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, DictationActivity::class.java)
    }
}