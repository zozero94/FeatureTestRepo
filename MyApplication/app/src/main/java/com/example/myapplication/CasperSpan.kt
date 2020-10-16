package com.example.myapplication

import android.content.Context
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.content.ContextCompat

class CasperSpan(
    val context: Context,
    val click: ((Long) -> Unit)? = null
) : ClickableSpan() {


    override fun onClick(widget: View) {
        click?.invoke(123)
    }

    override fun updateDrawState(ds: TextPaint) {
        ds.isUnderlineText = false
        ds.color = ContextCompat.getColor(context, R.color.white)
        ds.typeface = Typeface.create(ds.typeface, Typeface.BOLD)
        ds.bgColor = ContextCompat.getColor(context, R.color.black)
    }
}
