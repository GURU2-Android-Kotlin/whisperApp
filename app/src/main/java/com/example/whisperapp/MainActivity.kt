package com.example.whisperapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.example.whisperapp.community.MainCommuActivity
import com.example.whisperapp.event.EventListActivity
import com.example.whisperapp.portfolio.PortMainActivity
import com.example.whisperapp.portfolio.PortRegActivity
import com.example.whisperapp.todo.Todo
import com.example.whisperapp.todo.TodoActivity
import io.realm.Realm
import io.realm.kotlin.where
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    lateinit var todoButton: ImageButton
    lateinit var btn_port : ImageButton
    lateinit var btn_awards : ImageButton
    lateinit var btn_commu : ImageButton
    lateinit var btn_event: ImageButton
    lateinit var btn_my:ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todoButton = findViewById(R.id.todoButton)
        btn_port = findViewById(R.id.btn_port)
        btn_awards = findViewById(R.id.btn_awards)
        btn_commu = findViewById(R.id.btn_commu)
        btn_event=findViewById(R.id.btn_event)
        btn_my=findViewById(R.id.btn_my)

        todoButton.setOnClickListener { view ->
            startActivity<TodoActivity>()
        }
        btn_port.setOnClickListener { view ->
            startActivity<PortMainActivity>()
        }
        btn_awards.setOnClickListener { view ->
            startActivity<AwardsActivity>()
        }
        btn_commu.setOnClickListener { view ->
            startActivity<MainCommuActivity>()
        }
        btn_event.setOnClickListener { view ->
            startActivity<EventListActivity>()
        }
        btn_my.setOnClickListener {
            startActivity<mypageMainActivity>()
        }
    }

}