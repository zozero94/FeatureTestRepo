package com.example.myapplication.ui.kasper

import android.content.Context
import android.text.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class KasperEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    companion object {
        private val englishKasper = "^/(?:casper|kasper) ".toRegex(RegexOption.IGNORE_CASE)
        private val koreanKasper = "^/(?:캐스퍼|케스퍼) ".toRegex(RegexOption.IGNORE_CASE)
    }

    init {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                findKasper()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //do nothing
            }

        })
    }

    private fun findKasper() {
        val curText = text ?: return

        val isExistSpan = isExistKasperSpan()
        val kasperCommand = getKasperCommand()

        if (isExistSpan) {// [/캐스퍼] 블록이 지워질 때
            checkKasperBlockRemove(curText)
        }

        if (kasperCommand != null && !isExistSpan) {// [/캐스퍼] 가 입력되었을 때, 스팬을 입힘
            spanKasper(kasperCommand, curText)
        }
    }

    private fun isExistKasperSpan() =
        text?.getSpans(0, 0, KasperSpan::class.java)?.firstOrNull() != null


    private fun getKasperCommand(): String? {
        val isEnglish = englishKasper.find(text.toString())?.groups?.first()
        val isKorean = koreanKasper.find(text.toString())?.groups?.first()
        return when {
            isEnglish != null -> "/kasper"
            isKorean != null -> "/캐스퍼"
            else -> null
        }
    }

    private fun checkKasperBlockRemove(editable: Editable) {
        val string = editable.toString()
        val splitString = string.split(' ').first()
        val command = editable.getSpans(0, 0, KasperSpan::class.java).first().kasperCommand

        if (splitString != command) {
            val deleteLength =
                if (splitString.length > command.length) command.length else splitString.length
            editable.delete(0, deleteLength)
        } else if (string == command) {
            editable.clear()
        }

    }

    private fun spanKasper(kasperCommand: String, curText: Editable) {
        val span = KasperSpan(context, kasperCommand)
        curText.setSpan(span, 0, kasperCommand.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        curText.setSpan(spanWatcher, 0, curText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        curText.replace(
            0,
            kasperCommand.length,
            kasperCommand
        ) // replace 하면서, addTextChangedListener 가 다시 불림
        setSelection(curText.length)
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
//            if (what is KasperSpan) {
//                val newText = SpannableStringBuilder(text.subSequence(0, nstart))
//                    .append(text.subSequence(nend, text.length))
//                setTextWithSelection(newText, nstart)
//            }
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
                getSpans(oStart, oStart, KasperSpan::class.java).firstOrNull()?.let {
                    val spanStart = getSpanStart(it)
                    val spanEnd = getSpanEnd(it)
                    if (((spanStart + spanEnd) / 2) < oStart) {
                        setSelection(spanEnd)
                    } else {
                        setSelection(spanStart, spanEnd)
                    }
                }
            } else {
                val nStart = getSpans(oStart, oStart, KasperSpan::class.java).firstOrNull()?.let {
                    getSpanStart(it)
                }
                val nEnd = getSpans(oEnd, oEnd, KasperSpan::class.java).firstOrNull()?.let {
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

}