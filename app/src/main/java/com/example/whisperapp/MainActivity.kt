package com.example.whisperapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    lateinit var todobutton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todobutton=findViewById(R.id.todobutton)

        todobutton.setOnClickListener { view ->
            startActivity<TodoActivity>()
        }
    }
}