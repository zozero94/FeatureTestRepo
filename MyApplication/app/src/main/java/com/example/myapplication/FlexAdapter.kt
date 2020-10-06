package com.example.myapplication

import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item.view.*
import java.text.BreakIterator
import java.util.*

class FlexAdapter(@LayoutRes private val layoutRes: Int) : RecyclerView.Adapter<ViewHolder>() {
    private val listItem = LinkedList<String>()
    var onClick: ((String) -> Unit)? = null

    @MainThread
    fun replaceItem(list: List<String>) {
        listItem.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        ).apply {
            itemView.setOnClickListener {
                onClick?.invoke(listItem[adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(listItem[position])


}


data class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    var textSelectedListener: ((Int) -> Unit)? = null

    private lateinit var spans: SpannableString

    fun bind(s: String) {
        containerView.item.text = s
//        spans = containerView.item.text as SpannableString
//        setSpannable(s)
        containerView.button1.setOnClickListener {
            Toast.makeText(containerView.context, "버튼 1", Toast.LENGTH_SHORT).show()
        }
        containerView.button2.setOnClickListener {
            Toast.makeText(containerView.context, "버튼 1", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setSpannable(text: CharSequence) {
        val iterator = BreakIterator.getWordInstance().apply {
            setText(text.toString())
        }
        var start = iterator.first()
        var end = iterator.next()

        while (end != BreakIterator.DONE) {
            spanClickable(start, end)
            start = end
            end = iterator.next()
        }
    }

    private fun spanClickable(start: Int, end: Int) {
        spans.setSpan(getClickableSpan(start), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    private fun getClickableSpan(start: Int): ClickableSpan {
        return object : ClickableSpan() {
            override fun onClick(widget: View) {
                Log.d("zero", "onclick : $start")
                textSelectedListener?.invoke(start)
            }

            override fun updateDrawState(ds: TextPaint) {
            }
        }
    }
}