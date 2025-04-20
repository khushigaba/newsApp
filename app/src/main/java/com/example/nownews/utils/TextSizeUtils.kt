package com.example.nownews.utils

import android.content.Context
import android.widget.TextView

object TextSizeUtils {
    private const val PREFS_NAME = "NewsAppPrefs"
    private const val KEY_LARGE_TEXT = "isLargeText"

    // Save text size preference
    fun setLargeTextEnabled(context: Context, isLargeText: Boolean) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_LARGE_TEXT, isLargeText).apply()
    }

    // Get text size preference
    fun isLargeTextEnabled(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_LARGE_TEXT, false)
    }

    // Apply text size to a TextView
    fun applyTextSize(context: Context, textView: TextView, normalSizeSp: Float, largeSizeSp: Float) {
        val isLargeText = isLargeTextEnabled(context)
        textView.textSize = if (isLargeText) largeSizeSp else normalSizeSp
    }
}