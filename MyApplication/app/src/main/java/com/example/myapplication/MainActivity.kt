package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fuckingLongString = (1..10).map {
            getString(R.string.long_text)
        }.toString()
        textView.text = fuckingLongString
        textView.setTextSelectListener {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }

    }


}