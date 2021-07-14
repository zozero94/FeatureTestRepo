package com.example.myapplication.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ListItemMainBinding

class MainAdapter(var onClick: ((Intent?) -> Unit)? = null) :
    ListAdapter<TestType, MainAdapter.MainViewHolder>(object : DiffUtil.ItemCallback<TestType>() {
        override fun areItemsTheSame(oldItem: TestType, newItem: TestType): Boolean =
            oldItem.text == newItem.text

        override fun areContentsTheSame(oldItem: TestType, newItem: TestType): Boolean =
            oldItem == newItem

    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ListItemMainBinding.inflate(LayoutInflater.from(parent.context))
        return MainViewHolder(binding).apply {
            itemView.setOnClickListener { onClick?.invoke(currentList[adapterPosition].intent) }
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) =
        holder.bind(currentList[position].text)

    override fun getItemCount(): Int = currentList.size

    data class MainViewHolder(private val binding: ListItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String) {
            binding.button.text = text
        }
    }
}