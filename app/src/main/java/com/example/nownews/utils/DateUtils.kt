package com.example.nownews.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import java.util.*
import android.text.format.DateUtils
import android.util.Log
import java.text.ParseException

object DateUtils {

    private const val TAG = "DateUtils"
    private val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    private val outputFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).apply {
        timeZone = TimeZone.getDefault()
    }
    private val fallbackFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).apply {
        timeZone = TimeZone.getDefault()
    }

    fun formatDateTime(dateTime: String): String {
        if (dateTime.isNullOrBlank()) {
            Log.w(TAG, "formatDateTime: Input is null or blank")
            return "Invalid Date"
        }
        if (!isValidIso8601(dateTime)) {
            Log.w(TAG, "formatDateTime: Invalid ISO 8601 format: $dateTime")
            return "Invalid Date"
        }
        return synchronized(inputFormat) {
            try {
                val date = inputFormat.parse(dateTime)
                date?.let { outputFormat.format(it) } ?: run {
                    Log.w(TAG, "formatDateTime: Parsed date is null for input: $dateTime")
                    "Invalid Date"
                }
            } catch (e: ParseException) {
                Log.e(TAG, "formatDateTime: Parse error for input: $dateTime", e)
                "Invalid Date"
            } catch (e: Exception) {
                Log.e(TAG, "formatDateTime: Unexpected error for input: $dateTime", e)
                "Invalid Date"
            }
        }
    }
    fun getRelativeTime(dateString: String): String {
        if (dateString.isNullOrBlank()) {
            Log.w(TAG, "getRelativeTime: Input is null or blank")
            return "Unknown time"
        }
        if (!isValidIso8601(dateString)) {
            Log.w(TAG, "getRelativeTime: Invalid ISO 8601 format: $dateString")
            return "Unknown time"
        }
        return synchronized(inputFormat) {
            try {
                val date = inputFormat.parse(dateString)
                date?.let {
                    val now = System.currentTimeMillis()
                    val diff = now - it.time
                    if (diff < 0) {
                        Log.w(TAG, "getRelativeTime: Future date detected: $dateString")
                        return fallbackFormat.format(date)
                    }
                    val seconds = diff / 1000
                    val minutes = seconds / 60
                    val hours = minutes / 60
                    val days = hours / 24
                    when {
                        minutes < 60 -> "$minutes minutes ago"
                        hours < 24 -> "$hours hours ago"
                        days < 7 -> "$days days ago"
                        else -> fallbackFormat.format(date)
                    }
                } ?: run {
                    Log.w(TAG, "getRelativeTime: Parsed date is null for input: $dateString")
                    "Unknown time"
                }
            } catch (e: ParseException) {
                Log.e(TAG, "getRelativeTime: Parse error for input: $dateString", e)
                "Unknown time"
            } catch (e: Exception) {
                Log.e(TAG, "getRelativeTime: Unexpected error for input: $dateString", e)
                "Unknown time"
            }
        }
    }
    private fun isValidIso8601(dateString: String): Boolean {
        return dateString.matches(Regex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z"))
    }
}
