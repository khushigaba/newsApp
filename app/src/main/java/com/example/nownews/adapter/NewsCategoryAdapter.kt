package com.example.nownews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nownews.databinding.ItemNewsCategoryBinding
import com.example.nownews.model.NewsCategory
import com.example.nownews.utils.TextSizeUtils

class NewsCategoryAdapter(
    private val categories: List<NewsCategory>,
    private val onItemClick: (NewsCategory) -> Unit
) : RecyclerView.Adapter<NewsCategoryAdapter.NewsCategoryViewHolder>() {

    inner class NewsCategoryViewHolder(private val binding: ItemNewsCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: NewsCategory) {
            binding.apply {
                tvCategoryName.text = category.category.capitalize()

                // Apply text size
                TextSizeUtils.applyTextSize(root.context, tvCategoryName, 18f, 26f)

                root.setOnClickListener { onItemClick(category) }
            }
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
