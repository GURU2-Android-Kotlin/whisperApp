package com.example.whisperapp

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.exceptions.RealmMigrationNeededException
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class PortRegActivity : AppCompatActivity() {

    //lateinit var imageView_port: ImageView
    lateinit var edtTitle_port: EditText
    lateinit var edtDate_port: EditText
    lateinit var edtContent_port: EditText
    lateinit var edtMemo_port: EditText
    lateinit var deleteFab_port: FloatingActionButton
    lateinit var doneFab_port: FloatingActionButton

    val realm1 = try {
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.getInstance(config)
    } catch (ex: RealmMigrationNeededException) {
        Realm.getDefaultInstance()
    }
    val Gallery = 0
    var imgPath: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg_port)

        //imageView_port = findViewById<ImageView>(R.id.imageView_port)
        edtTitle_port = findViewById(R.id.edtTitle_port)
        edtDate_port = findViewById(R.id.edtDate_port)
        edtContent_port = findViewById(R.id.edtContent_port)
        edtMemo_port = findViewById(R.id.edtMemo_port)
        deleteFab_port = findViewById(R.id.deleteFab_port)
        doneFab_port = findViewById(R.id.doneFab_port)

        val id = intent.getLongExtra("id", -1L)
        if (id == -1L) {
            insertMode_port()
        } else {
            updateMode_port(id)
        }
    }

    private fun insertMode_port() {
        deleteFab_port.visibility = View.GONE
        doneFab_port.setOnClickListener { insertTodo_port() }
    }

    private fun updateMode_port(id: Long) {
        val listdb = realm1.where<portDB>().equalTo("id", id).findFirst()!!
        edtTitle_port.setText(listdb.title)
        edtDate_port.setText(listdb.date)
        edtContent_port.setText(listdb.content)
        edtMemo_port.setText(listdb.memo)

        doneFab_port.setOnClickListener { updateTodo_port(id) }
        deleteFab_port.setOnClickListener { deleteTodo_port(id) }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm1.close()
    }

    private fun insertTodo_port() {
        realm1.beginTransaction()

        val newItem = realm1.createObject<portDB>(nextId())
        //newItem.img = byteArrayOf()
        newItem.title = edtTitle_port.text.toString()
        newItem.date = edtDate_port.text.toString()
        newItem.content = edtContent_port.text.toString()
        newItem.memo = edtMemo_port.text.toString()
        realm1.commitTransaction()
        Toast.makeText(this, "저장되었습니다", Toast.LENGTH_SHORT).show()
    }

    private fun updateTodo_port(id: Long) {
        realm1.beginTransaction()

        val updateItem = realm1.where<portDB>().equalTo("id", id).findFirst()!!
        updateItem.title = edtTitle_port.text.toString()
        updateItem.date = edtDate_port.text.toString()
        updateItem.content = edtContent_port.text.toString()
        updateItem.memo = edtMemo_port.text.toString()

        realm1.commitTransaction()
        Toast.makeText(this, "변경되었습니다", Toast.LENGTH_SHORT).show()
    }

    private fun deleteTodo_port(id: Long) {
        realm1.beginTransaction()

        val deleteItem = realm1.where<portDB>().equalTo("id", id).findFirst()!!
        deleteItem.deleteFromRealm()

        realm1.commitTransaction()

        Toast.makeText(this, "삭제되었습니다", Toast.LENGTH_SHORT).show()
    }

    private fun nextId(): Int {
        val maxId = realm1.where<portDB>().max("id")

        if (maxId != null) {
            return maxId.toInt() + 1
        }
        return 0
    }
}