package com.example.myapplication.ui.mediapipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class MediaPipeViewModel : ViewModel() {

    private var framesPerSecond = 0
    private val timer = Timer()
    private var interval = 0
    private val _fps = MutableLiveData(0)
    fun getFPS(): LiveData<Int> = _fps


    fun checkFPS() {
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                framesPerSecond = interval
                interval = 0
            }

        }, 0, 1000)
    }

    fun increaseInterval() {
        interval++
        _fps.postValue(framesPerSecond)
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }
}