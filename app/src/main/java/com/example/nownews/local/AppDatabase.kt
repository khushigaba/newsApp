package com.example.nownews.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nownews.local.dao.CommentDao
import com.example.nownews.model.Comment

@Database(
    entities = [Comment::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase {
    abstract fun commentDao(): CommentDao
}