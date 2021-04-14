package com.example.myapplication.ui.shareui

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Surface
import android.view.WindowManager
import com.example.myapplication.R
import com.example.myapplication.databinding.WindowViewBinding

//TODO
// 1. SurfaceView 핸들링
// 2. Notification 띄우기
// 3. mediaProjection 관련 처리
class MediaProjectionService : Service() {
    companion object {
        private const val FOREGROUND_SERVICE_ID = 100
        private const val CHANNEL_ID = "MediaProjectionService"

        fun newService(context: Context, resultCode: Int, requestData: Intent) =
            firstStart(context).apply {
                putExtra(EXTRA_RESULT_CODE, resultCode)
                putExtra(EXTRA_REQUEST_DATA, requestData)
            }

        fun firstStart(context: Context) = Intent(context, MediaProjectionService::class.java)

    }

    private lateinit var mediaProjection: MediaProjection
    private lateinit var virtualDisplay: VirtualDisplay
    private val mediaProjectionManager: MediaProjectionManager by lazy {
        getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
    }

    private lateinit var binding: WindowViewBinding
    private lateinit var windowViewLayoutParams: WindowManager.LayoutParams

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
        Log.i("zero", "onCreate")
        startActivity(MediaProjectionActivity.newIntent(this))
        initBinding(getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        binding.create()
        startForegroundService()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.i("zero", "onStartCommand $intent")
        initMediaProjection(intent)
        return START_REDELIVER_INTENT
    }

    private fun startForegroundService() {
        createNotificationChannel()
        val notificationIntent = Intent(this, ShareUiActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )

        val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, CHANNEL_ID)
        } else {
            Notification.Builder(this)
        }
            .setContentTitle("Foreground Service")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(FOREGROUND_SERVICE_ID, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).apply {
                createNotificationChannel(serviceChannel)
            }
        }
    }

    private fun initBinding(layoutInflater: LayoutInflater) {
        binding = WindowViewBinding.inflate(layoutInflater, null, false)
        windowViewLayoutParams = WindowManager.LayoutParams(
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
        windowManager.addView(binding.root, windowViewLayoutParams)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun WindowViewBinding.create() {
        surfaceView.holder.addCallback(surfaceViewHolder)
        root.setOnTouchListener(WindowTouchEvent(updateViewLayout = ::updateViewPosition))
        btnStopService.setOnClickListener { stopSelf() }
    }

    private fun updateViewPosition(x: Int, y: Int) {
        windowViewLayoutParams.x += x
        windowViewLayoutParams.y += y
        windowManager.updateViewLayout(binding.root, windowViewLayoutParams)
    }

    private fun initMediaProjection(intent: Intent) {
        val data = intent.getParcelableExtra<Intent>(EXTRA_REQUEST_DATA) ?: return
        val resultCode = intent.getIntExtra(EXTRA_RESULT_CODE, Activity.RESULT_CANCELED)
        mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data)
        mediaProjection.registerCallback(object : MediaProjection.Callback() {
            override fun onStop() {
                super.onStop()
                Log.e("zero", "onStop")
            }
        }, null)
        val deviceSize = DeviceUtil.getDeviceSize(this)
        startMediaProjection(
            surface = binding.surfaceView.holder.surface,
            width = deviceSize.width,
            height = deviceSize.height
        )
    }


    private fun startMediaProjection(
        surface: Surface,
        projectionName: String = DEFAULT_VALUE_PROJECTION_NAME,
        width: Int = DEFAULT_VALUE_SIZE_WIDTH,
        height: Int = DEFAULT_VALUE_SIZE_HEIGHT
    ) {
        if (::mediaProjection.isInitialized) {
            virtualDisplay = mediaProjection.createVirtualDisplay(
                projectionName,
                width,
                height,
                application.resources.displayMetrics.densityDpi,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                surface,
                null,
                null
            )
            Log.e("zero", "successStartProjection")
        } else {
            Log.e("zero", "faileStartProjection")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopMediaProjection()
        if (::binding.isInitialized) {
            windowManager.removeView(binding.root)
        }
    }

    private fun stopMediaProjection() {
        try {
            if (::mediaProjection.isInitialized) {
                mediaProjection.stop()
            }
            if (::virtualDisplay.isInitialized) {
                virtualDisplay.release()
            }
        } catch (e: Exception) {
        }
    }
}