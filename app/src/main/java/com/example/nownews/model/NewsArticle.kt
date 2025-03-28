package com.example.nownews.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsArticle(
    val title: String,
    val description: String?,
    val url: String,
    val imageUrl: String?,
    val publishedAt: String,
    val source: String?
): Parcelable