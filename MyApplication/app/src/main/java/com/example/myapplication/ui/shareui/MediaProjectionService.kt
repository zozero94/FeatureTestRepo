package com.example.myapplication.ui.shareui

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import com.example.myapplication.databinding.WindowViewBinding

//TODO
// 1. SurfaceView 핸들링
// 2. Notification 띄우기
// 3. mediaProjection 관련 처리
class MediaProjectionService : Service() {
    companion object {
        fun newService(context: Context) = Intent(context, MediaProjectionService::class.java)
    }

    private lateinit var binding: WindowViewBinding

    private val windowManager: WindowManager by lazy {
        getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    private val surfaceViewHolder: SurfaceViewHolder by lazy {
        SurfaceViewHolder()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("zero", "보이진 않지만 나는 떳다.")
        initBinding(getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        binding.create()
    }

    private fun initBinding(layoutInflater: LayoutInflater) {
        binding = WindowViewBinding.inflate(layoutInflater, null, false)
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
            30, 30,  // X, Y 좌표
            WindowManager.LayoutParams.TYPE_TOAST
                .takeIf { Build.VERSION.SDK_INT < Build.VERSION_CODES.O }
                ?: WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
        }

        windowManager.addView(binding.root, params)
    }

    private fun WindowViewBinding.create() {
        surfaceView.holder.addCallback(surfaceViewHolder)
//        root.setOnTouchListener(WindowTouchEvent(updateViewLayout = ::updateViewPosition))
        btnStopService.setOnClickListener { stopSelf() }
    }

}