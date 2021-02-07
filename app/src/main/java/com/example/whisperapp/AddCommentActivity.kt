package com.example.whisperapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.util.*

class AddCommentActivity : AppCompatActivity() {

    lateinit var add_editText:EditText
    lateinit var button:Button
    lateinit var button2:Button

    val realm1= Realm.getDefaultInstance()
    val calendar: Calendar = java.util.Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.comment_add)

        add_editText=findViewById(R.id.add_editText)
        button=findViewById(R.id.button)
        button2=findViewById(R.id.button2)

        val id=intent.getLongExtra("id",-1L)
        if (id==-1L){
            insertMode_addcomment()
        }else{
            updateMode_addcomment(id)
        }

        button.setOnClickListener {  super.onBackPressed()}
    }

    private fun insertMode_addcomment(){
        button2.setOnClickListener { insertTodo_addcomment() }
    }

    private fun updateMode_addcomment(id:Long){
        val comment=realm1.where<Commu_commentDB>().equalTo("id",id).findFirst()!!
        add_editText.setText(comment.comment)

        button2.setOnClickListener { updateTodo_addcomment(id) }
    }
    override fun onDestroy() {
        super.onDestroy()
        realm1.close()
    }

    private fun insertTodo_addcomment(){
        realm1.beginTransaction()
        val newItem=realm1.createObject<Commu_commentDB>(nextId())
        newItem.comment=add_editText.text.toString()
        newItem.date=calendar.timeInMillis

        realm1.commitTransaction()
        alert("댓글이 저장 되었습니다."){
            yesButton { finish() }
        }.show()
    }

    private fun updateTodo_addcomment(id:Long){
        realm1.beginTransaction()
        val updateItem = realm1.where<Commu_commentDB>().equalTo("id", id).findFirst()!!
        updateItem.comment=add_editText.text.toString()
        updateItem.date=calendar.timeInMillis

        realm1.commitTransaction()

        alert("일정이 변경 되었습니다") {
            yesButton { finish() }
        }.show()
    }

    // Realm은 자동 키 증가를 지원하지 않으므로 아래 메서드를 만들었음
    private fun nextId() : Int {
        val maxId = realm1.where<Commu_commentDB>().max("id")
        // where<Todo>() : 테이블의 모든 값을 얻어옴
        // .max(필드명) : 현재 '필드명'중 가장 큰 값을 얻음 (Number형)

        if (maxId != null) {
            return maxId.toInt() + 1
        }
        return 0
    }
}