package com.example.nownews.model

enum class NewsCategory(val category: String) {
    BUSINESS("business"),
    ENTERTAINMENT("entertainment"),
    GENERAL("general"),
    HEALTH("health"),
    SCIENCE("science"),
    SPORTS("sports"),
    TECHNOLOGY("technology");

    companion object {
        fun fromString(value: String): NewsCategory? {
            return values().find { it.category.equals(value, ignoreCase = true) }
        }
    }
}
