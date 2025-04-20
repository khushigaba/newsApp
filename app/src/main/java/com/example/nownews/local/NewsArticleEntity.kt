package com.example.nownews.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_articles")
data class NewsArticleEntity(
    @PrimaryKey val url: String, // Unique identifier for the article
    val title: String,
    val imageUrl: String?,
    val publishedAt: String,
    val source: String?,
    val description: String?
)
