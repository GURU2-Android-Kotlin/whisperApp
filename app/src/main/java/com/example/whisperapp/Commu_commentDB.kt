package com.example.whisperapp

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Commu_commentDB (
    @PrimaryKey var id: Long = 0,
    var comment: String="",
    var date: Long=0
): RealmObject(){
}
