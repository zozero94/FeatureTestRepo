package com.example.myapplication.ui.main

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.alarm.AlarmPickerActivity
import com.example.myapplication.ui.books.BooksActivity
import com.example.myapplication.ui.dictation.DictationActivity
import com.example.myapplication.ui.kasper.KasperActivity
import com.example.myapplication.ui.mediapipe.MediaPipeActivity
import com.example.myapplication.ui.mlkit.MlkitActivity
import com.example.myapplication.ui.shareui.ShareUiActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class TestType(val intent: Intent) {
    class Kasper(intent: Intent) : TestType(intent)

    class Dictation(intent: Intent) : TestType(intent)

    class AlarmPicker(intent: Intent) : TestType(intent)

    class MediaPipe(intent: Intent) : TestType(intent)

    class Mlkit(intent: Intent) : TestType(intent)

    class Books(intent: Intent) : TestType(intent)

    class ShareUi(intent: Intent) : TestType(intent)

    companion object {
        fun createTestList(context: Context): List<TestType> {
            return listOf(
                Kasper(KasperActivity.newIntent(context)),
                Dictation(DictationActivity.newIntent(context)),
                AlarmPicker(AlarmPickerActivity.newIntent(context)),
                MediaPipe(MediaPipeActivity.newIntent(context)),
                Mlkit(MlkitActivity.newIntent(context)),
                Books(BooksActivity.newIntent(context)),
                ShareUi(ShareUiActivity.newIntent(context))
            )
        }
    }
}

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val listItem = MutableLiveData<List<TestType>>()
    fun getItemList(): LiveData<List<TestType>> = listItem

    fun setTestItems(testList: List<TestType>) {
        listItem.value = testList
    }

}
