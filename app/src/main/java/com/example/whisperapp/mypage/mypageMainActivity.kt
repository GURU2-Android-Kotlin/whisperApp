package com.example.whisperapp.mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.whisperapp.R
import com.example.whisperapp.login.LoginActivity
import com.example.whisperapp.login.Person
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.exceptions.RealmMigrationNeededException
import io.realm.kotlin.where

class mypageMainActivity : AppCompatActivity() {
    lateinit var id_my: TextView
    lateinit var email_my: TextView

    lateinit var my_appout: Button
    lateinit var app_finish: Button

    val loginRealm = try {
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.getInstance(config)
    } catch (ex: RealmMigrationNeededException) {
        Realm.getDefaultInstance()
    }

    var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mypage_activity_main)

        id_my=findViewById(R.id.id_my)
        email_my=findViewById(R.id.email_my)

        my_appout=findViewById(R.id.my_appout)
        app_finish=findViewById(R.id.app_finish)

        handler = Handler()

        val person=loginRealm.where<Person>().findFirst()

        if (person != null) {
            id_my.text= person.id
            email_my.text=person.email
        }

        my_appout.setOnClickListener {
            Toast.makeText(this, "회원 탈퇴가 완료되었습니다.", Toast.LENGTH_SHORT).show()
            deleteTodo(id_my.text as String)
        }
        app_finish.setOnClickListener {
            finishAffinity()
            Toast.makeText(this, "Application이 종료되었습니다.", Toast.LENGTH_SHORT).show()
            System.runFinalization()
            System.exit(0)}
    }

    // 데이터 베이스 할 일 삭제
    private fun deleteTodo(id: String) {
        loginRealm.beginTransaction()

        val deleteItem = loginRealm.where<Person>().equalTo("id", id).findFirst()!!
        deleteItem.deleteFromRealm()

        loginRealm.commitTransaction()


        finishAffinity()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        System.exit(0)
    }
}