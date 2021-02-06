package com.example.whisperapp

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class portDB (
    @PrimaryKey
    var id : Long = 0,
    var img : String = "",
    var title: String = "",
    var date: String = "",
    var content: String = "",
    var memo: String = ""
) : RealmObject() {}