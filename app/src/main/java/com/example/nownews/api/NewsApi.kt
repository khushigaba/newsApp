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

                val newsArticle = NewsArticle(
                    title = title,
                    description = description,
                    url = url,
                    imageUrl = imageUrl,
                    publishedAt = publishedAt
                )

                newsList.add(newsArticle)
            }

            newsList
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

//    fun getLocationList(count: Int?, offset: Int?): List<Location> {
//        val limit: Int = 25!!
//        val off: Int = 0!!
//        val locations: MutableList<Location> = mutableListOf()
//
//        val cookies = mutableListOf<Cookie>()
//        cookies.add(Cookie(CookieType.AUTHORIZATION, "Token $authToken"))
//        val request = HttpRequest(
//            RequestMethod.GET,
//            "$HTTP_PROTOCOL://$server/api/stock/location/?structural=false&offset=$off&limit=$limit",
//            cookies,
//            null,
//            String::class.java
//        )
//
//        val response = request.getResponse()
//
//        if (response.code != HttpURLConnection.HTTP_OK) {
//            Log.d("LocationList", "Error: Request failed with response code ${response.code}")
//            return locations
//        }
//
//        val jsonObject = response.getAsJson()!!.asJsonObject
//        val jsonArray = jsonObject.getAsJsonArray("results")
//
//        jsonArray.forEach {
//            val obj = it.asJsonObject
//            val id = obj.get("pk").asInt
//            val name = obj.get("name").asString
//            val description = obj.get("description").asString
//            val pathstring = obj.get("pathstring").asString
//            val items = obj.get("items").asInt
//            val sublocations = obj.get("sublocations").asInt
//            locations.add(Location(id, name, description, pathstring, items, sublocations))
//        }
//        return locations
//    }


}