package com.example.nownews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nownews.databinding.ItemNewsBinding
import com.example.nownews.model.NewsArticle
import com.example.nownews.utils.DateUtils

class NewsAdapter(
    private val newsList: List<NewsArticle>,
    private val onItemClick: (NewsArticle) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(private val itemNewsBinding: ItemNewsBinding) : RecyclerView.ViewHolder(itemNewsBinding.root) {
        fun bind(news: NewsArticle) {
            itemView.apply {
                itemNewsBinding.title.text = news.title
                itemNewsBinding.date.text = DateUtils.formatDateTime(news.publishedAt)

                // Load image using Glide
                Glide.with(context)
                    .load(news.imageUrl)
                    .into(itemNewsBinding.image)

                // Set click listener
                setOnClickListener {
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
