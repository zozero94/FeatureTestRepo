package com.example.myapplication.ui.shareui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMediaprojectionBinding
import com.example.myapplication.ui.base.BaseActivity

class MediaProjectionActivity : BaseActivity<ActivityMediaprojectionBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_mediaprojection

    companion object {
        fun newIntent(context: Context) = Intent(context, MediaProjectionActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mediaProjectionManager =
            getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        val result: ActivityResultLauncher<Intent> =
            registerForActivityResult(MediaProjectionResultContract(this)) {
                startService()
                finish()
            }
        result.launch(mediaProjectionManager.createScreenCaptureIntent())
    }

    private fun startService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(MediaProjectionService.newService(this))
        } else {
            startService(MediaProjectionService.newService(this))
        }
    }
}

internal class MediaProjectionResultContract(private val context: Context) :
    ActivityResultContract<Intent, Intent>() {

    override fun createIntent(context: Context, input: Intent?): Intent =
        input!!

    override fun parseResult(resultCode: Int, intent: Intent?): Intent? {
        return if (resultCode == Activity.RESULT_OK && intent != null) {
            MediaProjectionService.newService(context)
        } else {
            null
        }
    }
}