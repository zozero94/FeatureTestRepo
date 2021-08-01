package com.example.myapplication.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ListItemMainBinding

class MainAdapter(private val onClick: ((Intent?) -> Unit)) :
    ListAdapter<TestType, MainAdapter.MainViewHolder>(object : DiffUtil.ItemCallback<TestType>() {
        override fun areItemsTheSame(oldItem: TestType, newItem: TestType): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: TestType, newItem: TestType): Boolean =
            oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ListItemMainBinding.inflate(LayoutInflater.from(parent.context))
        return MainViewHolder(binding).apply {
            itemView.setOnClickListener {
                onClick.invoke(currentList[absoluteAdapterPosition].intent)
            }
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) =
        holder.bind(currentList[position]::class.java.simpleName)

    override fun getItemCount(): Int = currentList.size

    data class MainViewHolder(private val binding: ListItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String) {
            binding.button.text = text
        }
    }
}