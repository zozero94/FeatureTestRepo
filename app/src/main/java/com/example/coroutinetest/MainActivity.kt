package com.example.coroutinetest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutinetest.data.Item
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.title.view.*

class MainActivity : AppCompatActivity() {

    private val adapter = ApiAdapter()
    private val viewModel = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = MainViewModel() as T
    }.create(MainViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.adapter = adapter

        button.setOnClickListener {
            viewModel.requestApi()
        }

    }

    class ApiAdapter : RecyclerView.Adapter<ViewHolder>() {
        private val item = mutableListOf<Item>()

        fun updateItem(item: List<Item>) = with(this.item) {
            clear()
            addAll(item)
        }

        override fun getItemViewType(position: Int): Int = item[position].type.ordinal


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            when (Item.Type.values()[viewType]) {
                Item.Type.HEADER -> {
                    ViewHolder.Header(
                        LayoutInflater.from(parent.context).inflate(R.layout.title, parent, false)
                    )
                }

                Item.Type.FOOTER -> {
                    ViewHolder.Footer(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.list_item, parent, false)
                    )
                }
            }


        override fun getItemCount(): Int = item.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(item[position])
        }


    }

    sealed class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        abstract fun bind(item: Item)

        class Header(override val containerView: View) : ViewHolder(containerView) {
            override fun bind(item: Item) {
                containerView.title.text = item.result.titleDisplay
            }
        }

        class Footer(override val containerView: View) : ViewHolder(containerView) {
            override fun bind(item: Item) {
                containerView.author.text = item.result.authorDisplay.joinToString(", \n")
            }
        }

    }

}