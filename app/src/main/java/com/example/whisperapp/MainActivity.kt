package com.example.whisperapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    lateinit var todobutton: Button
    lateinit var btn_port: Button
    lateinit var eventBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todobutton=findViewById(R.id.todobutton)
        btn_port = findViewById(R.id.btn_port)
        eventBtn=findViewById(R.id.eventBtn)

        todobutton.setOnClickListener { view ->
            startActivity<TodoActivity>()
        }
        btn_port.setOnClickListener {view ->
            startActivity<PortMainActivity>()
        }
        eventBtn.setOnClickListener { view->
            startActivity<EventListActivity>()
        }
    }
}