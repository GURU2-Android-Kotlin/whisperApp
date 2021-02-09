package com.example.whisperapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.example.whisperapp.community.MainCommuActivity
import com.example.whisperapp.event.EventListActivity
import com.example.whisperapp.login.Person
import com.example.whisperapp.mypage.mypageMainActivity
import com.example.whisperapp.portfolio.PortMainActivity
import com.example.whisperapp.portfolio.portDB
import com.example.whisperapp.todo.Todo
import com.example.whisperapp.todo.TodoActivity
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.exceptions.RealmMigrationNeededException
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    lateinit var todoButton: ImageButton
    lateinit var btn_port : ImageButton
    lateinit var btn_awards : ImageButton
    lateinit var btn_commu : ImageButton
    lateinit var btn_event: ImageButton
    lateinit var btn_my:ImageButton
    lateinit var todoDateTextView : TextView
    lateinit var todoDateTextView1 : TextView
    lateinit var todoTitleTextView : TextView
    lateinit var todoTitleTextView1 : TextView

    lateinit var textView2:TextView

    val realm = try {
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.getInstance(config)
    } catch (ex: RealmMigrationNeededException) {
        Realm.getDefaultInstance()
    }

    val loginRealm = try {
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.getInstance(config)
    } catch (ex: RealmMigrationNeededException) {
        Realm.getDefaultInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todoButton = findViewById(R.id.todoButton)
        btn_port = findViewById(R.id.btn_port)
        btn_awards = findViewById(R.id.btn_awards)
        btn_commu = findViewById(R.id.btn_commu)
        btn_event=findViewById(R.id.btn_event)
        btn_my=findViewById(R.id.btn_my)
        todoDateTextView = findViewById(R.id.todoDateTextView)
        todoDateTextView1 = findViewById(R.id.todoDateTextView1)
        todoTitleTextView = findViewById(R.id.todoTitleTextView)
        todoTitleTextView1 = findViewById(R.id.todoTitleTextView1)
        textView2=findViewById(R.id.textView2)

        realm.beginTransaction()

        val person=loginRealm.where<Person>().findFirst()
        if (person != null) {
            textView2.text= person.id
        }
        val newItem = realm.createObject<portDB>(nextId_awards())
        newItem.title = "제목"
        newItem.date = "팀"
        newItem.classification = "19/01/01 - 21/12/21"
        newItem.content = "이것저것 하였음"
        newItem.memo = "다음에는 저것이것으로 해보기"

        val newItem1 = realm.createObject<Todo>(nextId_todo())
        newItem1.date = 21032013022303
        newItem1.title = "목록 1"

        val newItem2 = realm.createObject<Todo>(nextId_todo())
        newItem2.date = 21032013022303
        newItem2.title = "목록 2"
        realm.commitTransaction()

        val realmResult_main = realm.where<Todo>().findAll()
        val realmResultmain1 = realmResult_main[0]
        val realmResultmain2 = realmResult_main[1]

        if (realmResultmain1 != null) {
            todoDateTextView.text = android.text.format.DateFormat.format("yyyy/MM/dd", realmResultmain1.date)
            todoTitleTextView.text = realmResultmain1.title
        }
        if (realmResultmain2 != null) {
            todoDateTextView1.text = android.text.format.DateFormat.format("yyyy/MM/dd", realmResultmain2.date)
            todoTitleTextView1.text = realmResultmain2.title
        }

        todoButton.setOnClickListener { view ->
            startActivity<TodoActivity>()
        }
        btn_port.setOnClickListener { view ->
            startActivity<PortMainActivity>()
        }
        btn_awards.setOnClickListener { view ->
            startActivity<AwardsActivity>()
        }
        btn_commu.setOnClickListener { view ->
            startActivity<MainCommuActivity>()
        }
        btn_event.setOnClickListener { view ->
            startActivity<EventListActivity>()
        }
        btn_my.setOnClickListener {
            startActivity<mypageMainActivity>()
        }
    }
    private fun nextId_awards(): Int {
        val maxId = realm.where<portDB>().max("id")

        if (maxId != null) {
            return maxId.toInt() + 1
        }
        return 0
    }
    private fun nextId_todo(): Int {
        val maxId = realm.where<Todo>().max("id")

        if (maxId != null) {
            return maxId.toInt() + 1
        }
        return 0
    }
}