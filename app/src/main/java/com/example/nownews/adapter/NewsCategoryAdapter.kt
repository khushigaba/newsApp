package com.example.nownews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nownews.databinding.ItemNewsCategoryBinding
import com.example.nownews.model.NewsCategory

class NewsCategoryAdapter(
    private val categories: List<NewsCategory>,
    private val onItemClick: (NewsCategory) -> Unit
) : RecyclerView.Adapter<NewsCategoryAdapter.NewsCategoryViewHolder>() {

    inner class NewsCategoryViewHolder(private val binding: ItemNewsCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: NewsCategory) {
            binding.tvCategoryName.text = category.category.capitalize()
            binding.root.setOnClickListener { onItemClick(category) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsCategoryViewHolder {
        val binding = ItemNewsCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsCategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size
}
