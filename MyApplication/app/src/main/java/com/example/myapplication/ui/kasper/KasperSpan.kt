package com.example.myapplication.ui.kasper

import android.content.Context
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.content.ContextCompat
import com.example.myapplication.R

class KasperSpan(
    private val context: Context,
    val kasperCommand: String
) : ClickableSpan() {


    override fun onClick(widget: View) {}

    override fun updateDrawState(ds: TextPaint) {
        ds.isUnderlineText = false
        ds.color = ContextCompat.getColor(
            context,
            R.color.white
        )
        ds.typeface = Typeface.create(ds.typeface, Typeface.BOLD)
        ds.bgColor = ContextCompat.getColor(
            context,
            R.color.black
        )
    }
}
