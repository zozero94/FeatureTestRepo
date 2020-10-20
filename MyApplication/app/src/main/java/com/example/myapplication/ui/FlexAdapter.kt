package com.example.myapplication.ui

import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item.view.*
import java.util.*

class FlexAdapter(@LayoutRes private val layoutRes: Int) : RecyclerView.Adapter<ViewHolder>() {
    private val listItem = LinkedList<String>()
    var onClick: ((Int) -> Unit)? = null


    @MainThread
    fun replaceItem(list: List<String>) {
        listItem.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        )
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(listItem[position]).apply {
            onClick = holder.textSelectedListener
        }


}


data class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    var textSelectedListener: ((Int) -> Unit)? = null
    private lateinit var spans: SpannableString


    fun bind(s: String) {
        containerView.item.text = s
        spans = containerView.item.text as SpannableString
        containerView.item.movementMethod = LinkMovementMethod.getInstance()
    }



}