package com.example.nownews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nownews.databinding.ItemNewsBinding
import com.example.nownews.model.NewsArticle
import com.example.nownews.utils.DateUtils
import com.example.nownews.utils.TextSizeUtils

class NewsAdapter(
    private val newsList: List<NewsArticle>,
    private val onItemClick: (NewsArticle) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(private val itemNewsBinding: ItemNewsBinding) : RecyclerView.ViewHolder(itemNewsBinding.root) {
        fun bind(news: NewsArticle) {
            itemNewsBinding.apply {
                title.text = news.title
                date.text = DateUtils.formatDateTime(news.publishedAt)

                // Load image using Glide
                Glide.with(root.context)
                    .load(news.imageUrl)
                    .into(image)

                // Apply text size
                TextSizeUtils.applyTextSize(root.context, title, 16f, 24f)
                TextSizeUtils.applyTextSize(root.context, date, 12f, 20f)

                // Set click listener
                root.setOnClickListener {
                    Toast.makeText(root.context, "Opening ${news.title}", Toast.LENGTH_SHORT).show()
                    onItemClick(news)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsBinding.inflate(layoutInflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int = newsList.size
}
