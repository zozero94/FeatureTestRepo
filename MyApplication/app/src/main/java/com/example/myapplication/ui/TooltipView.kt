package com.example.myapplication.ui

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.myapplication.R

class TooltipView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle
) : AppCompatTextView(context, attributeSet, defStyleAttr) {

    init {
        background = ContextCompat.getDrawable(context, R.drawable.kasper_bg_9)
        text = "캐스퍼입니다 까꿍~?"

    }
}