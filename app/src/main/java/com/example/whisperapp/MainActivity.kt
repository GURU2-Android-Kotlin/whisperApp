package com.example.whisperapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.whisperapp.community.MainCommuActivity
import com.example.whisperapp.event.EventListActivity
import com.example.whisperapp.portfolio.PortMainActivity
import com.example.whisperapp.todo.TodoActivity
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    lateinit var todobutton: ImageButton
    lateinit var btn_port: ImageButton
    lateinit var eventBtn :ImageButton
    lateinit var btn_commu : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todobutton=findViewById(R.id.todobutton)
        btn_port = findViewById(R.id.btn_port)
        eventBtn=findViewById(R.id.eventBtn)
        btn_commu = findViewById(R.id.btn_commu)

        todobutton.setOnClickListener { view ->
            startActivity<TodoActivity>()
        }
        btn_port.setOnClickListener {view ->
            startActivity<PortMainActivity>()
        }
        eventBtn.setOnClickListener { view->
            startActivity<EventListActivity>()
        }
        btn_commu.setOnClickListener { view ->
            startActivity<MainCommuActivity>()
        }
    }
}