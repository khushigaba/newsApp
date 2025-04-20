package com.example.nownews.ui.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.nownews.R
import com.example.nownews.databinding.ActivityNewsDetailsBinding
import com.example.nownews.model.NewsArticle
import com.example.nownews.utils.DateUtils

class NewsDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailsBinding
    private var newsArticle: NewsArticle? = null // Class-level property
    private val TAG = "NewsDetailsActivity"
    private var isLargeText = false // Track text size state

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)  // Show back arrow
            setHomeAsUpIndicator(R.drawable.arrow_back_24px) // Optional custom back arrow
            title = "News Details"  // Set the desired title
        }


        // Get NewsArticle object from intent
        newsArticle = intent.getParcelableExtra<NewsArticle>("newsArticle")
        newsArticle?.let {
            Log.d(TAG, "Received article: ${it.title}, URL: ${it.url}")
            displayNewsDetails(it)
        } ?: Log.e(TAG, "No NewsArticle received from intent")

        // View Full Article button click
        binding.viewFullArticleButton.setOnClickListener {
            newsArticle?.url?.let { url ->
                Log.d(TAG, "Opening full article: $url")
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            } ?: Log.e(TAG, "No URL available for full article")
        }
    }

    // Inflate the options menu (for share functionality)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d(TAG, "Inflating menu")
        menuInflater.inflate(R.menu.menu_news_details, menu)
        return true
    }

    // Handle menu item clicks (for share functionality)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "Menu item selected: ${item.itemId}")
        return when (item.itemId) {
            android.R.id.home -> {
                Log.d(TAG, "Back button pressed")
                onBackPressed()
                true
            }
            R.id.action_share -> {
                Log.d(TAG, "Share action triggered")
                newsArticle?.let { article ->
                    if (article.title.isNullOrEmpty() || article.url.isNullOrEmpty()) {
                        Log.e(TAG, "Cannot share: title or URL is empty")
                        android.widget.Toast.makeText(this, "Cannot share this article", android.widget.Toast.LENGTH_SHORT).show()
                        return@let
                    }
                    val shareText = "${article.title}\n${article.url}"
                    Log.d(TAG, "Sharing text: $shareText")
                    val shareIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, shareText)
                        type = "text/plain"
                    }
                    try {
                        startActivity(Intent.createChooser(shareIntent, "Share Article"))
                        Log.d(TAG, "Share sheet opened")
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to open share sheet: ${e.message}")
                        android.widget.Toast.makeText(this, "Unable to share article", android.widget.Toast.LENGTH_SHORT).show()
                    }
                } ?: run {
                    Log.e(TAG, "No article available to share")
                    android.widget.Toast.makeText(this, "No article to share", android.widget.Toast.LENGTH_SHORT).show()
                }
                true
            }
            R.id.action_text_size -> {
                isLargeText = !isLargeText
                updateTextSize()
                true
            }
            else -> {
                Log.d(TAG, "Unknown menu item: ${item.itemId}")
                super.onOptionsItemSelected(item)
            }
        }

    }


    private fun displayNewsDetails(newsArticle: NewsArticle) {
        binding.apply {
            // Load header image using Glide
            Glide.with(this@NewsDetailsActivity)
                .load(newsArticle.imageUrl)
                .into(headerImage)

            // Set news details
            newsTitle.text = newsArticle.title
            newsSource.text = newsArticle.source
            newsDate.text = DateUtils.formatDateTime(newsArticle.publishedAt)
            newsDescription.text = newsArticle.description ?: "No description available."
        }
    }

    private fun updateTextSize() {
        binding.apply {
            if (isLargeText) {
                newsTitle.textSize = 28f // Larger title size (in sp)
                newsDescription.textSize = 20f // Larger description size (in sp)
            } else {
                newsTitle.textSize = 24f // Default title size (in sp)
                newsDescription.textSize = 16f // Default description size (in sp)
            }
        }
        Log.d(TAG, "Text size toggled to ${if (isLargeText) "large" else "normal"}")
    }
}
