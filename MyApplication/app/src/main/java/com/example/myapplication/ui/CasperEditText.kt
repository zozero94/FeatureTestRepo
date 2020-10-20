package com.example.myapplication.ui

import android.content.Context
import android.text.SpanWatcher
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class CasperEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    /**
     * CharSequence 를 받아 Casper 문자에 Span 을 입힌다.
     */
    fun findCasper() {
        val isCasper = caspers.find(text.toString())?.groups?.first()
        val isExistSpan = text?.getSpans(0, 0, CasperSpan::class.java)?.firstOrNull()
        if (isCasper != null && isExistSpan == null) {
            val span = CasperSpan(context)

            val ssb = SpannableStringBuilder()
                .append(CASPER)
                .append(" ")

            ssb.setSpan(
                span,
                0,
                CASPER.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            setTextWithSelection(ssb, CASPER.length + 1)

        }

    }

    private val spanWatcher = object : SpanWatcher {

        override fun onSpanChanged(
            text: Spannable,
            what: Any,
            ostart: Int,
            oend: Int,
            nstart: Int,
            nend: Int
        ) {
            if (what is CasperSpan) {
                val newText = SpannableStringBuilder(text.subSequence(0, nstart))
                    .append(text.subSequence(nend, text.length))
                setTextWithSelection(newText, nstart)
            }
        }

        override fun onSpanRemoved(text: Spannable?, what: Any?, start: Int, end: Int) {
            //do nothing
        }

        override fun onSpanAdded(text: Spannable?, what: Any?, start: Int, end: Int) {
            //do nothing
        }
    }

    private fun setTextWithSelection(ssb: SpannableStringBuilder, cursorIndex: Int) {
        text = ssb
        text?.setSpan(spanWatcher, 0, ssb.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        setSelection(cursorIndex)
    }

    override fun onSelectionChanged(oStart: Int, oEnd: Int) {
        text?.apply {
            if (oStart == oEnd) {
                getSpans(oStart, oStart, CasperSpan::class.java).firstOrNull()?.let {
                    val spanStart = getSpanStart(it)
                    val spanEnd = getSpanEnd(it)
                    if (((spanStart + spanEnd) / 2) < oStart) {
                        setSelection(spanEnd)
                    } else {
                        setSelection(spanStart, spanEnd)
                    }
                }
            } else {
                val nStart = getSpans(oStart, oStart, CasperSpan::class.java).firstOrNull()?.let {
                    getSpanStart(it)
                }
                val nEnd = getSpans(oEnd, oEnd, CasperSpan::class.java).firstOrNull()?.let {
                    getSpanEnd(it)
                }
                if (nStart != null && nEnd != null) {
                    setSelection(nStart, nEnd)
                } else if (nStart != null) {
                    setSelection(nStart, oEnd)
                } else if (nEnd != null) {
                    setSelection(oStart, nEnd)
                }
            }
        }
        super.onSelectionChanged(oStart, oEnd)
    }

    companion object {
        private val caspers = "^/(?:casper|kasper|캐스퍼|케스퍼)".toRegex(RegexOption.IGNORE_CASE)
        const val CASPER = "/캐스퍼"
    }
}