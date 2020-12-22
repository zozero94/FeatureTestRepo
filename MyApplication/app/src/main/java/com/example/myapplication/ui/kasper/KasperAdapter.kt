package com.example.myapplication.ui.kasper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ListItemKasperBinding
import java.util.*

class KasperAdapter : RecyclerView.Adapter<KasperAdapter.KasperViewHolder>() {

    private val inputTextList = LinkedList<String>()

    fun insertText(text: String) {
        inputTextList.add(text)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KasperViewHolder {
        val binding = ListItemKasperBinding.inflate(LayoutInflater.from(parent.context))
        return KasperViewHolder(binding)
    }


    override fun onBindViewHolder(holder: KasperViewHolder, position: Int) =
        holder.bind(inputTextList[position])

    override fun getItemCount(): Int = inputTextList.size

    data class KasperViewHolder(private val binding: ListItemKasperBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String) {
            binding.listText.text = text
        }
    }
}