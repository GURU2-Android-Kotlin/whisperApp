package com.example.whisperapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.whisperapp.portfolio.portDB
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.exceptions.RealmMigrationNeededException
import io.realm.kotlin.where
import java.util.*

class AwardsActivity : AppCompatActivity() {

    lateinit var tvTitle_awards : TextView
    lateinit var tvDate_awards : TextView
    lateinit var tvClassification_awards : TextView
    lateinit var tvContent_awards : TextView
    lateinit var tvMemo_awards : TextView

    val realm99 = try {
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.getInstance(config)
    } catch (ex: RealmMigrationNeededException) {
        Realm.getDefaultInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.awards_activity_main)

        tvTitle_awards = findViewById(R.id.tvTitle_awards)
        tvDate_awards = findViewById(R.id.tvDate_awards)
        tvClassification_awards = findViewById(R.id.tvClassification_awards)
        tvContent_awards= findViewById(R.id.tvContent_awards)
        tvMemo_awards = findViewById(R.id.tvMemo_awards)

        val realmResult99 = realm99.where<portDB>().findAll()
        val dbSize = realm99.where<portDB>().max("id")!!
        val random = Random()
        val sizeInt = random.nextInt(dbSize.toInt())

        val realmResult99_main = realmResult99[sizeInt]
        if (realmResult99_main != null) {
            tvTitle_awards.setText(realmResult99_main.title)
            tvDate_awards.setText(realmResult99_main.date)
            tvClassification_awards.setText(realmResult99_main.classification)
            tvContent_awards.setText(realmResult99_main.content)
            tvMemo_awards.setText(realmResult99_main.memo)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm99.close()
    }

    private fun nextId(): Int {
        val maxId = realm99.where<portDB>().max("id")

        if (maxId != null) {
            return maxId.toInt() + 1
        }
        return 0
    }
}