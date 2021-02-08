package com.example.whisperapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.example.whisperapp.community.MainCommuActivity
import com.example.whisperapp.event.EventListActivity
import com.example.whisperapp.portfolio.PortMainActivity
import com.example.whisperapp.todo.Todo
import com.example.whisperapp.todo.TodoActivity
import io.realm.Realm
import io.realm.kotlin.where
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

}