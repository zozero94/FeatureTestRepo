package com.example.myapplication.ui.kasper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_kasper.view.*
import java.util.*

class KasperAdapter : RecyclerView.Adapter<KasperAdapter.KasperViewHolder>() {

    private val inputTextList = LinkedList<String>()

    fun insertText(text: String) {
        inputTextList.add(text)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KasperViewHolder =
        KasperViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_kasper, parent, false)
        )

    override fun onBindViewHolder(holder: KasperViewHolder, position: Int) =
        holder.bind(inputTextList[position])

    override fun getItemCount(): Int = inputTextList.size

    data class KasperViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(text: String) {
            containerView.list_text.text = text
        }
    }
}