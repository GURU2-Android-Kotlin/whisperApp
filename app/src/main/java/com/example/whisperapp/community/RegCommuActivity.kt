package com.example.whisperapp.community

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.whisperapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.exceptions.RealmMigrationNeededException
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class RegCommuActivity : AppCompatActivity() {

    lateinit var edtTitle_commu : EditText
    lateinit var edtName_commu : EditText
    lateinit var edtContent_commu : EditText
    lateinit var doneFab_commu : FloatingActionButton

    val realm = try {
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.getInstance(config)
    } catch (ex: RealmMigrationNeededException) {
        Realm.getDefaultInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community_activity_reg)

        edtTitle_commu = findViewById(R.id.edtTitle_commu)
        edtName_commu = findViewById(R.id.edtName_commu)
        edtContent_commu = findViewById(R.id.edtContent_commu)
        doneFab_commu = findViewById(R.id.doneFab_commu)

        val id = intent.getLongExtra("id", -1L)
        if (id == -1L) {
            insertMode_commu()
        } else {
            updateMode_commu(id)
        }
    }

    private fun insertMode_commu() {
        doneFab_commu.setOnClickListener { insertTodo_commu() }
    }

    private fun updateMode_commu(id: Long) {
        val listdb = realm.where<CommuDB>().equalTo("id", id).findFirst()!!
        edtTitle_commu.setText(listdb.title)
        edtName_commu.setText(listdb.name)
        edtContent_commu.setText(listdb.content)

        doneFab_commu.setOnClickListener { updateTodo_commu(id) }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    private fun insertTodo_commu() {
        realm.beginTransaction()

        val newItem = realm.createObject<CommuDB>(nextId())
        //newItem.img = byteArrayOf()
        newItem.title = edtTitle_commu.text.toString()
        newItem.name = edtName_commu.text.toString()
        newItem.content = edtContent_commu.text.toString()

        realm.commitTransaction()
        Toast.makeText(this, "저장되었습니다", Toast.LENGTH_SHORT).show()
    }

    private fun updateTodo_commu(id: Long) {
        realm.beginTransaction()

        val updateItem = realm.where<CommuDB>().equalTo("id", id).findFirst()!!
        updateItem.title = edtTitle_commu.text.toString()
        updateItem.name = edtName_commu.text.toString()
        updateItem.content = edtContent_commu.text.toString()

        realm.commitTransaction()
        Toast.makeText(this, "변경되었습니다", Toast.LENGTH_SHORT).show()
    }

    private fun nextId(): Int {
        val maxId = realm.where<CommuDB>().max("id")

        if (maxId != null) {
            return maxId.toInt() + 1
        }
        return 0
    }
}