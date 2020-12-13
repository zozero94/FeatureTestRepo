package com.example.myapplication.ui.mediapipe

import android.content.Context
import android.content.Intent
import android.graphics.SurfaceTexture
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMediapipeBinding
import com.example.myapplication.ui.base.BaseActivity
import com.google.mediapipe.components.CameraHelper.CameraFacing
import com.google.mediapipe.components.CameraXPreviewHelper
import com.google.mediapipe.components.ExternalTextureConverter
import com.google.mediapipe.components.FrameProcessor
import com.google.mediapipe.components.PermissionHelper
import com.google.mediapipe.framework.AndroidAssetUtil
import com.google.mediapipe.glutil.EglManager


class MediaPipeActivity : BaseActivity<ActivityMediapipeBinding>() {
    override val layoutId: Int = R.layout.activity_mediapipe


    private var previewFrameTexture: SurfaceTexture? = null
    private val previewDisplayView: SurfaceView by lazy { SurfaceView(this) }

    private val eglManager: EglManager by lazy { EglManager(null) }
    private val processor: FrameProcessor by lazy {
        FrameProcessor(
            this,
            eglManager.nativeContext,
            BINARY_GRAPH_NAME,
            INPUT_VIDEO_STREAM_NAME,
            OUTPUT_VIDEO_STREAM_NAME
        )
    }
    private val converter: ExternalTextureConverter by lazy { ExternalTextureConverter(eglManager.context) }

    private var cameraHelper: CameraXPreviewHelper? = null

    init {
        System.loadLibrary("mediapipe_jni");
        System.loadLibrary("opencv_java3");
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupPreviewDisplayView();

        AndroidAssetUtil.initializeNativeAssetManager(this)
        processor.videoSurfaceOutput.setFlipY(FLIP_FRAMES_VERTICALLY)
        PermissionHelper.checkAndRequestCameraPermissions(this)

    }

    override fun onResume() {
        super.onResume()
        converter.setFlipY(FLIP_FRAMES_VERTICALLY)
        converter.setConsumer(processor)
        if (PermissionHelper.cameraPermissionsGranted(this)) {
            startCamera()
        }
    }

    override fun onPause() {
        super.onPause()
        converter.close()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    private fun setupPreviewDisplayView() {
        previewDisplayView.visibility = View.GONE
        binding.previewDisplayLayout.addView(previewDisplayView)
        previewDisplayView
            .holder
            .addCallback(
                object : SurfaceHolder.Callback {
                    override fun surfaceCreated(holder: SurfaceHolder) {
                        processor.videoSurfaceOutput.setSurface(holder.surface)
                    }

                    override fun surfaceChanged(
                        holder: SurfaceHolder,
                        format: Int,
                        width: Int,
                        height: Int
                    ) {
                        // Connect the converter to the camera-preview frames as its input (via
                        // previewFrameTexture), and configure the output width and height as the computed
                        // display size.
                        converter.setSurfaceTextureAndAttachToGLContext(
                            previewFrameTexture, width, height
                        )
                    }

                    override fun surfaceDestroyed(holder: SurfaceHolder) {
                        processor.videoSurfaceOutput.setSurface(null)
                    }
                })
    }


    private fun startCamera() {
        cameraHelper = CameraXPreviewHelper().apply {
            setOnCameraStartedListener { surfaceTexture ->
                previewFrameTexture = surfaceTexture
                // Make the display view visible to start showing the preview. This triggers the
                // SurfaceHolder.Callback added to (the holder of) previewDisplayView.
                previewDisplayView.visibility = View.VISIBLE
            }
            startCamera(this@MediaPipeActivity, CAMERA_FACING,  /*surfaceTexture=*/null)
        }
    }

    companion object {

        fun newIntent(context: Context) = Intent(context, MediaPipeActivity::class.java)

        private const val BINARY_GRAPH_NAME = "face_detection_mobile_gpu.binarypb"
        private const val INPUT_VIDEO_STREAM_NAME = "input_video"
        private const val OUTPUT_VIDEO_STREAM_NAME = "output_video"
        private val CAMERA_FACING = CameraFacing.FRONT
        private const val FLIP_FRAMES_VERTICALLY = true

    }
}