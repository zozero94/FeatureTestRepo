package com.example.myapplication.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_main.view.*
import java.util.*

class MainAdapter(var onClick: ((TestType) -> Unit)? = null) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private val itemList = LinkedList<TestType>()

    fun replaceItems(lists: List<TestType>) {
        itemList.addAll(lists)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_main, parent, false)
        ).apply {
            itemView.setOnClickListener { onClick?.invoke(itemList[adapterPosition]) }
        }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) =
        holder.bind(itemList[position].text)

    override fun getItemCount(): Int = itemList.size

    data class MainViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(text: String) {
            containerView.button.text = text
        }
    }
}