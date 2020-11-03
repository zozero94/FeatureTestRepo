package com.example.coroutinetest

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.annotation.ExampleAnnotation
import com.example.coroutinetest.databinding.ActivityMainBinding
import com.example.coroutinetest.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
@ExampleAnnotation("helloWorld")
class MainActivity : AppCompatActivity() {

    private val adapter = ApiAdapter()

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        recyclerView.adapter = adapter
        Zero().ZeroMainActivity(applicationContext)
        button.setOnClickListener {
            Toast.makeText(this, "눌림", Toast.LENGTH_SHORT).show()
            viewModel.requestApi()
        }

    }
}