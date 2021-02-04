package com.example.whisperapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where
import org.jetbrains.anko.startActivity

class TodoActivity : AppCompatActivity() {

    lateinit var fab:FloatingActionButton
    lateinit var listView: ListView
    val realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
        setSupportActionBar(findViewById(R.id.toolbar))

        listView=findViewById(R.id.listView)
        fab=findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            startActivity<EditTodoActivity>()
        }

        val realmResult = realm.where<Todo>().findAll().sort("date", Sort.DESCENDING)
        // 할 일 목록을 날짜순으로 모두 가져옴

        val adapter = TodoListAdapter(realmResult)  // 할 일 목록이 담긴 어댑터 생성
        listView.adapter = adapter    // 어댑터 지정 (이 때 목록이 출력됨)

        realmResult.addChangeListener { _ -> adapter.notifyDataSetChanged() }  // 데이터가 변경될 경우 어댑터에 적용됨
        // notifyDataSetChanged() : 데이터 변경을 통지하여 목록을 다시 출력함
        listView.setOnItemClickListener { parent, view, position, id ->   // 리스트 뷰 아이템 클릭시 처리
            startActivity<EditTodoActivity>("id" to id)   // 기존 id 존재 여부에 따라 새 할 일 추가 또는 수정
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        realm.close()
    }
}