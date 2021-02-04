package com.example.whisperapp

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Todo(
    @PrimaryKey var id: Long = 0,
    var title: String = "",
    var subtitle: String = "",
    var date: Long = 0
) : RealmObject() {
}
