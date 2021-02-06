package com.example.whisperapp

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort
import io.realm.exceptions.RealmMigrationNeededException
import io.realm.kotlin.where
import org.jetbrains.anko.startActivity

class PortMainActivity : AppCompatActivity() {

    lateinit var fab_port: FloatingActionButton
    lateinit var listView_port: ListView
    val realm1 = try {
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.getInstance(config)
    } catch (ex: RealmMigrationNeededException) {
        Realm.getDefaultInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_port)
        //setSupportActionBar(findViewById(R.id.toolbar_port))


        listView_port = findViewById(R.id.listView_port)
        fab_port = findViewById(R.id.fab_port)
        fab_port.setOnClickListener { view ->
            startActivity<PortRegActivity>()
        }

        val realmResult = realm1.where<portDB>().findAll().sort("id", Sort.ASCENDING)

        val adapter = PortAdapter(realmResult)
        listView_port.adapter = adapter

        realmResult.addChangeListener { _ -> adapter.notifyDataSetChanged() }
        listView_port.setOnItemClickListener { parent, view, position, id ->
            startActivity<PortRegActivity>("id" to id)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm1.close()
    }
}