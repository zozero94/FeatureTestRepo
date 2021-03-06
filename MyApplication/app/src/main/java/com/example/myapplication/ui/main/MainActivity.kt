package com.example.myapplication.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R.layout
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.alarm.AlarmPickerActivity
import com.example.myapplication.ui.base.BaseActivity
import com.example.myapplication.ui.books.BooksActivity
import com.example.myapplication.ui.dictation.DictationActivity
import com.example.myapplication.ui.kasper.KasperActivity
import com.example.myapplication.ui.mediapipe.MediaPipeActivity
import com.example.myapplication.ui.mlkit.MlkitActivity
import com.example.myapplication.ui.shareui.ShareUiActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutId: Int = layout.activity_main

    private val mainViewModel by viewModels<MainViewModel>()

    private val adapter by lazy {
        MainAdapter { intent ->
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = mainViewModel
        binding.mainContent.adapter = adapter
        binding.mainContent.layoutManager = GridLayoutManager(this, 2)

        mainViewModel.setTestItems(
            listOf(
                TestType.KasperTest(KasperActivity.newIntent(this), "Kasper"),
                TestType.DictationTest(DictationActivity.newIntent(this), "Dictation"),
                TestType.AlarmPicker(AlarmPickerActivity.newIntent(this), "AlarmPicker"),
                TestType.MediaPipe(MediaPipeActivity.newIntent(this), "MediaPipe"),
                TestType.Mlkit(MlkitActivity.newIntent(this), "Mlkit"),
                TestType.Books(BooksActivity.newIntent(this), "Books"),
                TestType.ShareUi(ShareUiActivity.newIntent(this), "ShareUi")
            )
        )

        mainViewModel.getItemList().observe(this, {
            adapter.submitList(it)
        })
    }


}

