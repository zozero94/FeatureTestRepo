package com.example.myapplication.ui.mlkit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMlkitBinding
import com.example.myapplication.ui.base.BaseActivity

class MlkitActivity : BaseActivity<ActivityMlkitBinding>() {
    override val layoutId: Int = R.layout.activity_mlkit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    companion object {
        fun newIntent(context: Context) = Intent(context, MlkitActivity::class.java)
    }
}