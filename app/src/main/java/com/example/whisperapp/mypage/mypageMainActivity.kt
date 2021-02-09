package com.example.whisperapp.mypage

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock.sleep
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.whisperapp.R
import com.example.whisperapp.login.LoginActivity
import com.example.whisperapp.login.Person
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.exceptions.RealmMigrationNeededException
import io.realm.kotlin.where
import kotlinx.coroutines.delay as delay1


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
            var dialog = AlertDialog.Builder(this)
            dialog.setTitle(" 회원 탈퇴하시겠습니까? ")
            dialog.setMessage("확인 버튼을 누르실 경우, 회원님의 소중한\n개인정보가 모두 삭제됩니다.")
            dialog.setIcon(R.mipmap.ic_launcher)

            fun toast_p() {
                deleteTodo(id_my.text as String)
            }
            fun toast_n(){
                super.onBackPressed()
            }

            var dialog_listener = object: DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    when(which){
                        DialogInterface.BUTTON_POSITIVE ->
                            toast_p()
                        DialogInterface.BUTTON_NEGATIVE ->
                            toast_n()
                    }
                }
            }

            dialog.setPositiveButton("확인",dialog_listener)
            dialog.setNegativeButton("취소",dialog_listener)
            dialog.show()
        }

        app_finish.setOnClickListener {
            finishAffinity()
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