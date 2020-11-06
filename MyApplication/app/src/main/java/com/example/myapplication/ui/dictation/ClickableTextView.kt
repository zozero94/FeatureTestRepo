package com.example.myapplication.ui.dictation

import android.annotation.SuppressLint
import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.example.myapplication.R

@SuppressLint("ClickableViewAccessibility")
class ClickableTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {

    private val previousHighLightSet: MutableSet<Pair<Int, Int>> = mutableSetOf()
    private lateinit var spans: SpannableString

    init {
        setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
        }
    }

    private var textSelectedListener: ((Pair<Int, Int>) -> Unit)? = null
    private val gestureDetector =
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(event: MotionEvent): Boolean {
                val offset = getOffsetForPosition(event.x, event.y).coerceAtMost(length() - 1)

                if (text[offset] != ' ' && text[offset] != '\n') {
                    val wordIdx = getWordIndex(offset)
                    textSelectedListener?.invoke(wordIdx)
                    highLightText(listOf(wordIdx))
                }

                return true
            }
        })

    fun setMainContent(text: String) {
        super.setText(text, BufferType.SPANNABLE)
        spans = this.text as SpannableString
        movementMethod = LinkMovementMethod.getInstance()
    }

    fun setTextSelectListener(listener: (Pair<Int, Int>) -> Unit) {
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

    fun highLightText(wordIdx: List<Pair<Int, Int>>) {
        removePreviousHighLight()
        wordIdx.forEach(::highLight)
    }

    private fun removePreviousHighLight() {
        if (previousHighLightSet.isNotEmpty()) {
            previousHighLightSet.forEach(::spanNormal)
            previousHighLightSet.clear()
        }
    }

    private fun highLight(index: Pair<Int, Int>) {
        previousHighLightSet.add(index)
        spanHighLight(index)
    }

    private fun spanNormal(spanIndex: Pair<Int, Int>) {
        spans.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.black)),
            spanIndex.first,
            spanIndex.second,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spans.setSpan(
            BackgroundColorSpan(ContextCompat.getColor(context, R.color.white)),
            spanIndex.first,
            spanIndex.second,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    private fun spanHighLight(spanIndex: Pair<Int, Int>) {
        spans.setSpan(
            BackgroundColorSpan(ContextCompat.getColor(context, R.color.white)),
            spanIndex.first,
            spanIndex.second,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spans.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.red)),
            spanIndex.first,
            spanIndex.second,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }


}