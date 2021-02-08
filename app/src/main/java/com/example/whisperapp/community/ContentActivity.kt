package com.example.whisperapp.community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.whisperapp.CommentListAdapter
import com.example.whisperapp.R
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where
import org.jetbrains.anko.startActivity

class ContentActivity : AppCompatActivity() {

    lateinit var titlesee_commu: TextView
    lateinit var contentsee_commu: TextView
    lateinit var button_content_commu: Button
    lateinit var commentListView:ListView
    lateinit var nameContentTextView:TextView

    val realm1= Realm.getDefaultInstance() //Realm 인스턴스 얻기

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community_activity_content)

        titlesee_commu=findViewById(R.id.titlesee_commu)
        contentsee_commu=findViewById(R.id.contentsee_commu)
        button_content_commu=findViewById(R.id.button_content_commu)
        commentListView=findViewById(R.id.commentListView)
        nameContentTextView=findViewById(R.id.nameContentTextView)

        button_content_commu.setOnClickListener {
            startActivity<AddCommentActivity>()
        }

        val realmResult = realm1.where<Commu_commentDB>().findAll().sort("id", Sort.ASCENDING)


        val adapter= CommentListAdapter(realmResult)
        commentListView.adapter=adapter

        realmResult.addChangeListener { _ -> adapter.notifyDataSetChanged() }  // 데이터가 변경될 경우 어댑터에 적용됨
        // notifyDataSetChanged() : 데이터 변경을 통지하여 목록을 다시 출력함


        val id=intent.getLongExtra("id",-1L)

        if (id==-1L){
        }else{
            updateMode_comment(id)
        }
    }

    private fun updateMode_comment(id:Long){
        val comment1=realm1.where<CommuDB>().equalTo("id",id).findFirst()!!
        titlesee_commu.setText(comment1.title)
        nameContentTextView.setText(comment1.name)
        contentsee_commu.setText(comment1.content)
        //button_content_commu.setOnClickListener { updateTodo(id)}
    }


    override fun onDestroy() {
        super.onDestroy()
        realm1.close()
    }


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