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
import com.example.myapplication.util.toast

class MediaProjectionActivity : BaseActivity<ActivityMediaprojectionBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_mediaprojection

    companion object {
        fun newIntent(context: Context) =
            Intent(context, MediaProjectionActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mediaProjectionManager =
            getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        val result: ActivityResultLauncher<Intent> =
            registerForActivityResult(MediaProjectionResultContract(this)) {
                if (it == null) {
                    toast(this, "취소누름 ㅋ")
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(it)
                    } else {
                        startService(it)
                    }
                }
                finish()
            }
        result.launch(mediaProjectionManager.createScreenCaptureIntent())
    }
}

internal class MediaProjectionResultContract(private val context: Context) :
    ActivityResultContract<Intent, Intent?>() {

    override fun createIntent(context: Context, input: Intent?): Intent =
        input!!

    override fun parseResult(resultCode: Int, intent: Intent?): Intent? {
        return if (resultCode == Activity.RESULT_OK && intent != null) {
            MediaProjectionService.newService(context, resultCode, intent)
        } else {
            null
        }
    }
}