package com.example.whisperapp.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import com.example.whisperapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.exceptions.RealmMigrationNeededException
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.util.*

class EditTodoActivity : AppCompatActivity() {

    lateinit var datePickText: TextView
    lateinit var calendarView: CalendarView
    lateinit var deleteFab: FloatingActionButton
    lateinit var doneFab: FloatingActionButton
    lateinit var todoEditText: EditText
    lateinit var subEditText: EditText

    val realm= try {
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.getInstance(config)
    } catch (ex: RealmMigrationNeededException) {
        Realm.getDefaultInstance()
    } //Realm 인스턴스 얻기
    val calendar: Calendar = java.util.Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.todo_activity_edit)

        calendarView = findViewById(R.id.calendarView)
        todoEditText = findViewById(R.id.todoEditText)
        subEditText = findViewById(R.id.subEditText)
        datePickText = findViewById(R.id.datePickText)
        deleteFab = findViewById(R.id.deleteFab)
        doneFab = findViewById(R.id.doneFab)

        // 인텐트로 id를 전달해서 데이터 베이스의 삽입/변경/삭제를 분기
        // id=-1 (추가모드)
        val id=intent.getLongExtra("id",-1L)
        if (id==-1L){
            insertMode()
        }else{
            updateMode(id)
        }

        // 캘린더 뷰의 날짜를 선택했을 때 캘린더 객체에 설정
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calendar.set(java.util.Calendar.YEAR, year)
            calendar.set(java.util.Calendar.MONTH, month)
            calendar.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth)
            datePickText.text = String.format("%d / %d / %d", year, month + 1, dayOfMonth)
        }
    }
    private fun insertMode(){
        deleteFab.visibility= View.GONE
        doneFab.setOnClickListener { insertTodo() }
    }

    private fun updateMode(id:Long){
        val todo=realm.where<Todo>().equalTo("id",id).findFirst()!!
        todoEditText.setText(todo.title)
        subEditText.setText(todo.subtitle)
        calendarView.date=todo.date

        doneFab.setOnClickListener { updateTodo(id)}
        deleteFab.setOnClickListener { deleteTodo(id) }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
    private fun insertTodo(){
        realm.beginTransaction()
        val newItem=realm.createObject<Todo>(nextId())
        newItem.title=todoEditText.text.toString()
        newItem.subtitle=subEditText.text.toString()
        newItem.date=calendar.timeInMillis

        realm.commitTransaction()
        alert("일정이 저장 되었습니다."){
            yesButton { finish() }
        }.show()
    }
    // 데이터 베이스 할 일 변경
    private fun updateTodo(id: Long) {
        realm.beginTransaction()

        val updateItem = realm.where<Todo>().equalTo("id", id).findFirst()!!
        // where<Todo>() : 테이블의 모든 값을 얻어옴
        // .equalTo(필드명, Long) : 해당 '필드명'의 Long형 id값의 데이터를 가져옴
        // findFirst() : 첫 번째 데이터

        updateItem.title = todoEditText.text.toString()
        updateItem.subtitle = subEditText.text.toString()
        updateItem.date = calendar.timeInMillis
        // timeInMillis 프로퍼티 : 날짜를 가져오는 getTimeInMilles()

        realm.commitTransaction()

        alert("일정이 변경 되었습니다") {
            yesButton { finish() }
        }.show()
    }

    // 데이터 베이스 할 일 삭제
    private fun deleteTodo(id: Long) {
        realm.beginTransaction()

        val deleteItem = realm.where<Todo>().equalTo("id", id).findFirst()!!
        deleteItem.deleteFromRealm()

        realm.commitTransaction()

        alert("일정이 삭제 되었습니다") {
            yesButton { finish() }
        }.show()
    }

    // Realm은 자동 키 증가를 지원하지 않으므로 아래 메서드를 만들었음
    private fun nextId() : Int {
        val maxId = realm.where<Todo>().max("id")
        // where<Todo>() : 테이블의 모든 값을 얻어옴
        // .max(필드명) : 현재 '필드명'중 가장 큰 값을 얻음 (Number형)

        if (maxId != null) {
            return maxId.toInt() + 1
        }
        return 0
    }
}