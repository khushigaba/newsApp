package com.example.nownews.model

data class NewsArticle(
    val title: String,
    val description: String?,
    val url: String,
    val imageUrl: String?,
    val publishedAt: String
)