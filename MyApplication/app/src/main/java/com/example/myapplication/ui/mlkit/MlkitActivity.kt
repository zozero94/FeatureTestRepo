package com.example.myapplication.ui.mlkit

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMlkitBinding
import com.example.myapplication.ui.base.BaseActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseDetector
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import kotlinx.android.synthetic.main.activity_mlkit.*

class MlkitActivity : BaseActivity<ActivityMlkitBinding>() {
    override val layoutId: Int = R.layout.activity_mlkit

    private val options: PoseDetectorOptions by lazy {
        PoseDetectorOptions.Builder()
            .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
            .build()
    }

    private val poseDetector: PoseDetector by lazy { PoseDetection.getClient(options) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Base pose detector with streaming frames, when depending on the pose-detection sdk
        startCamera()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (checkPermission()) {
                startCamera()
            } else {
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun checkPermission() = PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewFinder.surfaceProvider)
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
            val analysis = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(
                        ContextCompat.getMainExecutor(this),
                        YourImageAnalyzer(poseDetector)
                    )
                }
            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, analysis)

            } catch (exc: Exception) {
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private class YourImageAnalyzer(private val poseDetector: PoseDetector) :
        ImageAnalysis.Analyzer {

        override fun analyze(imageProxy: ImageProxy) {
            val mediaImage = imageProxy.image
            if (mediaImage != null) {
                val image =
                    InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                // Pass image to an ML Kit Vision API
                // ...
                val result = poseDetector.process(image)
                    .addOnSuccessListener {
                        Log.e("zero", it.toString())
                    }
                    .addOnFailureListener {
                        Log.e("zero", it.message)
                    }
                Log.e("zero", result.toString())
            }


        }
    }

    companion object {
        const val REQUEST_CODE_PERMISSIONS = 100
        private val PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

        fun newIntent(context: Context) = Intent(context, MlkitActivity::class.java)
    }
}