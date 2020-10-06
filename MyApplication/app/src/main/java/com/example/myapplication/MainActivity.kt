package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.text.BreakIterator
import java.util.*

class MainActivity : AppCompatActivity() {
    private val flexAdapter =
        FlexAdapter(R.layout.list_item).apply {
            onClick = {
                Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        with(flexBox) {
            adapter = flexAdapter
            layoutManager = LinearLayoutManager(context)
        }

        button.setOnClickListener {
            val fuckingLongString = (1..10).map {
                getString(R.string.long_text)
            }.toString()
            val list = separateSentence(fuckingLongString)
            flexAdapter.replaceItem(list)
        }

    }


    private fun separateSentence(text: CharSequence): List<String> {
        val resultList = LinkedList<String>()
        val iterator = BreakIterator.getSentenceInstance().apply {
            setText(text.toString())
        }
        var start = iterator.first()
        var end = iterator.next()

        while (end != BreakIterator.DONE) {
            val word = text.substring(start, end)
            resultList.add(word)
            start = end
            end = iterator.next()
        }
        return resultList
    }
}