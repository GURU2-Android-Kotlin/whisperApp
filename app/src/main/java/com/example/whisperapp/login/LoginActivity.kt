package com.example.whisperapp.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.whisperapp.MainActivity
import com.example.whisperapp.R
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.exceptions.RealmMigrationNeededException
import org.jetbrains.anko.startActivity

class LoginActivity: AppCompatActivity(){
    lateinit var id: EditText
    lateinit var pwd: EditText
    lateinit var loginBtn: Button
    lateinit var joinBtn: Button

    //var loginRealm: Realm?=null
    var handler: Handler? = null

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
        setContentView(R.layout.login_login)

        handler = Handler()

        id=findViewById(R.id.id)
        pwd=findViewById(R.id.pwd)
        loginBtn=findViewById(R.id.loginBtn)
        joinBtn=findViewById(R.id.joinBtn)

        loginBtn.setOnClickListener {
            login()
        }

        joinBtn.setOnClickListener {
            Toast.makeText(this,"회원가입 창으로 이동", Toast.LENGTH_SHORT).show()

            startActivity<JoinActivity>()
        }
    }

    fun login(){
        val id=id.text.toString()
        val pwd=pwd.text.toString()

        val person=loginRealm?.where(Person::class.java)!!
                .equalTo("id", id)
                .equalTo("pwd", pwd)
                .findFirst()

        if(person!=null){
            Toast.makeText(this,"로그인 성공", Toast.LENGTH_SHORT).show()
            var intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
        }else{
            Toast.makeText(this,"로그인 정보가 없거나 틀립니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        loginRealm?.close()
    }
}