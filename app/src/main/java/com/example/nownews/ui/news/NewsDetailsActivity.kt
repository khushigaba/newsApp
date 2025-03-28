package com.example.nownews.ui.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
        val newsArticle = intent.getParcelableExtra<NewsArticle>("newsArticle")
        newsArticle?.let { displayNewsDetails(it) }

        // View Full Article button click
        binding.viewFullArticleButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(newsArticle?.url))
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
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
}
