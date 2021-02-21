package com.automosen.jsonposter.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post (
    @PrimaryKey(autoGenerate = false)
    val id : Int?,
    val userId : Int?,
    var title : String?,
    var body : String?
)