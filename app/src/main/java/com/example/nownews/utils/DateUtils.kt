package com.example.nownews.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import java.util.*
import android.text.format.DateUtils

object DateUtils {
    fun formatDateTime(dateTime: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        return try {
            val date = inputFormat.parse(dateTime)
            if (date != null) {
                outputFormat.format(date)
            } else {
                "Invalid Date"
            }
        } catch (e: Exception) {
            "Invalid Date"
        }
    }
    fun getRelativeTime(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date = inputFormat.parse(dateString)
            date?.let {
                val now = System.currentTimeMillis()
                val diff = now - it.time
                val seconds = diff / 1000
                val minutes = seconds / 60
                val hours = minutes / 60
                val days = hours / 24
                when {
                    minutes < 60 -> "$minutes minutes ago"
                    hours < 24 -> "$hours hours ago"
                    days < 7 -> "$days days ago"
                    else -> SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(it)
                }
            } ?: "Unknown time"
        } catch (e: Exception) {
            "Unknown time"
        }
    }
}
