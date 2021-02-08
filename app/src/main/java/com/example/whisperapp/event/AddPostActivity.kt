package com.example.whisperapp.event

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.whisperapp.R

class AddPostActivity: AppCompatActivity() {

    lateinit var titlePost:EditText
    lateinit var objectPost:EditText
    lateinit var hostPost:EditText
    lateinit var timePost:EditText
    lateinit var linkPost:EditText
    lateinit var detailPost:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_addpost)

        titlePost=findViewById(R.id.titlePost)
        objectPost=findViewById(R.id.objectPost)
        hostPost=findViewById(R.id.hostPost)
        timePost=findViewById(R.id.timePost)
        linkPost=findViewById(R.id.linkPost)
        detailPost=findViewById(R.id.detailPost)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.submit_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(this, "게시글 업로드", Toast.LENGTH_SHORT).show()
        val intent= Intent(this, EventListActivity::class.java)
        intent.putExtra("title", titlePost.text.toString())
        intent.putExtra("content", "대상: "+objectPost.text+"\n주최/주관: "+hostPost.text+"\n모집 기간: "+timePost.text)
        intent.putExtra("sort", "스터디/동아리")
        intent.putExtra("detail", detailPost.text.toString())
        intent.putExtra("mainImageView", "bob")
        intent.putExtra("link", linkPost.text.toString())
        startActivity(intent)
        return true
    }

}