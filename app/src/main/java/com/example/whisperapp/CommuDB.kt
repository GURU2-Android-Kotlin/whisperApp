package com.example.whisperapp

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CommuDB (
    @PrimaryKey var id: Long = 0,
    var title: String = "",
    var name: String ="",
    var content: String =""
) : RealmObject(){
}