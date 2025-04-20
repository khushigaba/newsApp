package com.example.nownews.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.nownews.local.dao.CommentDao
import com.example.nownews.model.Comment

@Database(
    entities = [Comment::class, NewsArticleEntity::class],
    version = 2,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase(){

    abstract fun commentDao(): CommentDao
    abstract fun newsArticleDao(): NewsArticleDao

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
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                    CREATE TABLE bookmarked_articles (
                        url TEXT PRIMARY KEY NOT NULL,
                        title TEXT NOT NULL,
                        imageUrl TEXT,
                        publishedAt TEXT NOT NULL,
                        source TEXT,
                        description TEXT
                    )
                """.trimIndent())
            }
        }
    }
}