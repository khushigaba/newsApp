package com.example.nownews.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nownews.local.dao.CommentDao
import com.example.nownews.model.Comment

@Database(
    entities = [Comment::class],
    version = 1
)

abstract class AppDatabase {
    abstract fun commentDao(): CommentDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "nownews_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}