package com.example.myapplication.ui.main

import android.app.Application
import android.content.Intent
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.ui.alarm.AlarmPickerActivity
import com.example.myapplication.ui.books.BooksActivity
import com.example.myapplication.ui.dictation.DictationActivity
import com.example.myapplication.ui.kasper.KasperActivity
import com.example.myapplication.ui.mediapipe.MediaPipeActivity
import com.example.myapplication.ui.mlkit.MlkitActivity

interface FeatureTest {
    @FeatureAnnotation("Kasper")
    fun kasperTest(newIntent: Intent): Pair<Intent, String>

    @FeatureAnnotation("Dictation")
    fun dictationTest(newIntent: Intent): Pair<Intent, String>

    @FeatureAnnotation("AlarmPicker")
    fun alarmPicker(newIntent: Intent): Pair<Intent, String>

    @FeatureAnnotation("MediaPipe")
    fun mediaPipe(newIntent: Intent): Pair<Intent, String>

    @FeatureAnnotation("Mlkit")
    fun mlkit(newIntent: Intent): Pair<Intent, String>

    @FeatureAnnotation("Books")
    fun books(newIntent: Intent): Pair<Intent, String>
}

class MainViewModel @ViewModelInject constructor(application: Application) :
    AndroidViewModel(application) {
    private val factory = TestType.newFactory(FeatureTest::class.java)

    private val features = listOf(
        factory.kasperTest(KasperActivity.newIntent(application)),
        factory.dictationTest(DictationActivity.newIntent(application)),
        factory.alarmPicker(AlarmPickerActivity.newIntent(application)),
        factory.mediaPipe(MediaPipeActivity.newIntent(application)),
        factory.mlkit(MlkitActivity.newIntent(application)),
        factory.books(BooksActivity.newIntent(application))
    )
    private val listItem = MutableLiveData(features)

    fun getItemList() = listItem


}
