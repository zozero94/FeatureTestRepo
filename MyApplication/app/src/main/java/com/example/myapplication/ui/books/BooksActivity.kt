package com.example.myapplication.ui.books

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.domain.Book
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityBooksBinding
import com.example.myapplication.databinding.ListItemBookBinding
import com.example.myapplication.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BooksActivity : BaseActivity<ActivityBooksBinding>() {
    override val layoutId: Int = R.layout.activity_books
    private val viewModel by viewModels<BooksViewModel>()

    private val bookAdapter by lazy {
        BookAdapter {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = this.viewModel
        binding.recyclerBook.adapter = bookAdapter
        binding.searchButton.setOnClickListener {
            viewModel.searchBook(binding.searchEdit.text.toString())
        }

        viewModel.bindBooks().observe(this, {
            bookAdapter.replaceItem(it)
        })
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, BooksActivity::class.java)
    }
}

class BookAdapter(private val onClick: ((Book) -> Unit)? = null) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private var bookList: List<Book> = emptyList()

    fun replaceItem(books: List<Book>) {
        bookList = books
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding =
            ListItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding).apply {
            binding.root.setOnClickListener {
                onClick?.invoke(bookList[adapterPosition])
            }
        }
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(bookList[position])
    }

    override fun getItemCount(): Int = bookList.size

    class BookViewHolder(private val binding: ListItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            binding.book = book
            Glide.with(binding.root)
                .load(book.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.bookImage)
        }
    }
}
