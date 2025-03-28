package com.example.nownews.utils

import java.text.SimpleDateFormat
import java.util.Locale

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
}
