package com.example.myapplication

import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item.view.*
import java.text.BreakIterator
import java.util.*
import java.util.concurrent.TimeUnit

class FlexAdapter(@LayoutRes private val layoutRes: Int) : RecyclerView.Adapter<ViewHolder>() {
    private val listItem = LinkedList<String>()
    var onClick: ((Int) -> Unit)? = null

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.clearDisposable()
    }

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
//                onClick?.invoke(listItem[adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(listItem[position]).apply {
            onClick = holder.textSelectedListener
        }



}


data class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    private var previous = 0
    var textSelectedListener: ((Int) -> Unit)? = null
    private lateinit var spans: SpannableString
    private val disposable= CompositeDisposable()


    fun bind(s: String) {
        containerView.item.text = s
        spans = containerView.item.text as SpannableString
        setSpannable(s)
        startHighLight()
    }

    fun clearDisposable(){
        disposable.clear()
    }

    private fun startHighLight() {
        Observable.interval(500, TimeUnit.MILLISECONDS)
            .map {
                it + 1
            }
            .map { it.toInt() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                spans.setSpan(
                    ForegroundColorSpan(
                        ContextCompat.getColor(
                            containerView.context,
                            R.color.red
                        )
                    ), previous, previous + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spans.setSpan(
                    ForegroundColorSpan(
                        ContextCompat.getColor(
                            containerView.context,
                            R.color.colorPrimaryDark
                        )
                    ), it, it + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                previous = it
            }.addTo(disposable)
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