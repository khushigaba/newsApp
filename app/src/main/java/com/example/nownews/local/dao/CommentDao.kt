package com.example.nownews.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.nownews.model.Comment
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {
    @Insert
    suspend fun insert(comment: Comment)

    @Query("SELECT * FROM comments WHERE articleId = :articleId ORDER BY timestamp DESC")
    fun getCommentsForArticle(articleId: String): Flow<List<Comment>>
}