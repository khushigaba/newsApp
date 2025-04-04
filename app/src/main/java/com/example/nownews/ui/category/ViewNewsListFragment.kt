package com.example.nownews.ui.category

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nownews.R
import com.example.nownews.adapter.NewsAdapter
import com.example.nownews.api.NewsApi
import com.example.nownews.databinding.FragmentViewNewsListBinding
import com.example.nownews.model.NewsArticle
import com.example.nownews.model.NewsCategory
import com.example.nownews.ui.home.HomeFragment
import com.example.nownews.ui.news.NewsDetailsActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewNewsListFragment : Fragment() {


    private val newsList = mutableListOf<NewsArticle>()
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var binding: FragmentViewNewsListBinding
    private var categoryName: String = NewsCategory.GENERAL.category //Default

    companion object {
        private const val TAG = "ViewNewsListFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentViewNewsListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryName = arguments?.getString("categoryName") ?: NewsCategory.GENERAL.category
// Update status bar title
        requireActivity().title = categoryName ?: "News List"

        // Setup RecyclerView
        newsAdapter = NewsAdapter(newsList) { newsArticle ->
            Log.d(TAG, "NewsArticle Item Click--------")
            Log.d(TAG, "title: ${newsArticle.title}")
            Log.d(TAG, "description: ${newsArticle.description}")
            Log.d(TAG, "date: ${newsArticle.publishedAt}")
            startActivity(
                Intent(context, NewsDetailsActivity::class.java).putExtra(
                    "newsArticle",
                    newsArticle
                )
            )
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = newsAdapter

        fetchNewsByCategory(categoryName)
    }


    private fun fetchNewsByCategory(categoryName: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val response = NewsApi.getTrendingNewsByCategory(categoryName)

            // Switch to Main Thread to update UI
            withContext(Dispatchers.Main) {
                if (response != null) {
                    // Handle success
                    newsList.addAll(response)
                    newsAdapter.notifyDataSetChanged()
                    binding.loadingProgress.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                } else {
                    // Handle error
                    binding.loadingProgress.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    Toast.makeText(requireContext(), "Failed to fetch news", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}