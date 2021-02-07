package com.example.whisperapp

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where
import org.jetbrains.anko.startActivity
import org.w3c.dom.Comment

class MainCommuActivity : AppCompatActivity() {

    lateinit var fab_comu:FloatingActionButton
    val realm = Realm.getDefaultInstance()
    lateinit var listView_commu: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_commu)

        listView_commu=findViewById(R.id.listView_commu)

        fab_comu=findViewById(R.id.fab_comu)
        fab_comu.setOnClickListener { view ->
            startActivity<RegCommuActivity>()
        }

        val realmResult = realm.where<CommuDB>().findAll().sort("id", Sort.ASCENDING)
        val adapter=CommuListAdapter(realmResult)
        listView_commu.adapter=adapter

        realmResult.addChangeListener { _ -> adapter.notifyDataSetChanged() }  // 데이터가 변경될 경우 어댑터에 적용됨
        // notifyDataSetChanged() : 데이터 변경을 통지하여 목록을 다시 출력함
        listView_commu.setOnItemClickListener { parent, view, position, id ->   // 리스트 뷰 아이템 클릭시 처리
            startActivity<ContentActivity>("id" to id)   // 기존 id 존재 여부에 따라 새 할 일 추가 또는 수정
        }


    }

    override fun onDestroy() {
        super.onDestroy()

        realm.close()
    }
}