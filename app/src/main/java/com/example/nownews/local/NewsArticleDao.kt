package com.example.nownews.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete

@Dao
interface NewsArticleDao {
    @Insert
    suspend fun insertArticle(article: NewsArticleEntity)

    @Delete
    suspend fun deleteArticle(article: NewsArticleEntity)

    @Query("SELECT * FROM bookmarked_articles")
    suspend fun getAllBookmarkedArticles(): List<NewsArticleEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarked_articles WHERE url = :url)")
    suspend fun isArticleBookmarked(url: String): Boolean
}