package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.content.Context
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent

@SuppressLint("ClickableViewAccessibility")
class ClickableTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {
    init {
        setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
        }
        movementMethod = LinkMovementMethod.getInstance()
    }

    private var textSelectedListener: ((Int) -> Unit)? = null
    private val gestureDetector =
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                val offset = getOffsetForPosition(e.x, e.y).coerceAtMost(length() - 1)

                if (text[offset] != ' ' && text[offset] != '\n') {
                    val wordIdx = getWordIndex(offset)
                    textSelectedListener?.invoke(wordIdx.first)
                }

                return true
            }
        })

    fun setTextSelectListener(listener: (Int) -> Unit) {
        textSelectedListener = listener
    }

    private fun getWordIndex(offset: Int): Pair<Int, Int> {
        var startIdx = offset
        var endIdx = offset

        // find startIndex
        try {
            while (text[startIdx - 1] != ' ' && text[startIdx - 1] != '\n') {
                startIdx--
            }
        } catch (ignored: StringIndexOutOfBoundsException) {
        }

        // find endIndex
        try {
            while (text[endIdx + 1] != ' ' && text[endIdx + 1] != '\n') {
                endIdx++
            }
        } catch (ignored: StringIndexOutOfBoundsException) {

        }

        return startIdx to endIdx + 1
    }
}