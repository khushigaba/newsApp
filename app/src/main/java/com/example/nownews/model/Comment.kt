package com.example.nownews.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "comments")

data class Comment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val articleId: String,  // Links to your news article
    val userName: String = "Guest",
    val text: String,
    val timestamp: Long = System.currentTimeMillis()

)
