package com.example.myapplication.ui.mediapipe

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMediapipeBinding
import com.example.myapplication.ui.base.BaseActivity


class MediaPipeActivity : BaseActivity<ActivityMediapipeBinding>() {
    override val layoutId: Int = R.layout.activity_mediapipe


    private var previewFrameTexture: SurfaceTexture? = null
    private var previewDisplayView: SurfaceView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (checkPermission()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
        previewDisplayView = SurfaceView(this)
        setupPreviewDisplayView();

        try {
            val applicationInfo =
                packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "Cannot find application info: $e")
        }
    }

    private fun checkPermission() = PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d(TAG, "requestCode : $requestCode , permission : $permissions")
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        previewDisplayView?.visibility = View.VISIBLE
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview)
            } catch (e: Exception) {
                Log.e("startCamera", "use case binding failed")
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun setupPreviewDisplayView() {
        previewDisplayView?.visibility = View.GONE
        val viewGroup: ViewGroup = binding.previewDisplayLayout
        viewGroup.addView(previewDisplayView)
        previewDisplayView?.holder?.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder?) {}

            override fun surfaceChanged(
                holder: SurfaceHolder?,
                format: Int,
                width: Int,
                height: Int
            ) {
                val viewSize = Size(width, height)
            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {}

        })

    }

    companion object {
        const val TAG = "ZERO"

        fun newIntent(context: Context) = Intent(context, MediaPipeActivity::class.java)
        private val PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 100
    }
}