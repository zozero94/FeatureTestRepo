package com.example.myapplication.ui.books

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityBooksBinding
import com.example.myapplication.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BooksActivity : BaseActivity<ActivityBooksBinding>() {
    override val layoutId: Int = R.layout.activity_books
    private val viewModel by viewModels<BooksViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = this.viewModel
        viewModel.searchBook("java")
        viewModel.books.observe(this, {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        })
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, BooksActivity::class.java)
    }
}