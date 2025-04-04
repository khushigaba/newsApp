package com.example.nownews.api

import com.example.nownews.model.NewsArticle
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.net.HttpURLConnection

object NewsApi {

    private const val API_KEY = "84c3ff866c444a14bc72dc0a64d683ed"

    fun getTrendingNews(): List<NewsArticle>? {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://newsapi.org/v2/top-headlines?country=us&apiKey=$API_KEY")
            .build()

        return try {
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""

            val jsonObject = JSONObject(responseBody)
            val articlesArray = jsonObject.getJSONArray("articles")

            val newsList = mutableListOf<NewsArticle>()

            for (i in 0 until articlesArray.length()) {
                val articleJson = articlesArray.getJSONObject(i)

                val title = articleJson.getString("title")
                val description = articleJson.optString("description", null)
                val url = articleJson.getString("url")
                val imageUrl = articleJson.optString("urlToImage", null)
                val publishedAt = articleJson.getString("publishedAt")
                val sourceName = articleJson.getJSONObject("source").optString("name", "")
                val newsArticle = NewsArticle(
                    title = title,
                    description = description,
                    url = url,
                    imageUrl = imageUrl,
                    publishedAt = publishedAt,
                    source = sourceName
                )

                newsList.add(newsArticle)
            }

            newsList
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    fun getTrendingNewsByCategory(category: String): List<NewsArticle>? {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://newsapi.org/v2/top-headlines?country=us&apiKey=$API_KEY&category=$category")
            .build()

        return try {
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""

            val jsonObject = JSONObject(responseBody)
            val articlesArray = jsonObject.getJSONArray("articles")

            val newsList = mutableListOf<NewsArticle>()

            for (i in 0 until articlesArray.length()) {
                val articleJson = articlesArray.getJSONObject(i)

                val title = articleJson.getString("title")
                val description = articleJson.optString("description", null)
                val url = articleJson.getString("url")
                val imageUrl = articleJson.optString("urlToImage", null)
                val publishedAt = articleJson.getString("publishedAt")
                val sourceName = articleJson.getJSONObject("source").optString("name", "")
                val newsArticle = NewsArticle(
                    title = title,
                    description = description,
                    url = url,
                    imageUrl = imageUrl,
                    publishedAt = publishedAt,
                    source = sourceName
                )

                newsList.add(newsArticle)
            }

            newsList
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


}