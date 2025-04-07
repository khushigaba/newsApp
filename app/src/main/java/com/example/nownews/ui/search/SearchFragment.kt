package com.example.nownews.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.nownews.databinding.FragmentSearchBinding
import com.example.nownews.model.NewsArticle
import com.example.nownews.ui.category.ViewNewsListFragment
import com.example.nownews.ui.news.NewsDetailsActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFragment : Fragment() {

    private val newsList = mutableListOf<NewsArticle>()
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var binding: FragmentSearchBinding

    companion object {
        private const val TAG = "SearchFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.searchButton.setOnClickListener {
            val searchQuery = binding.searchEditText.text.toString()
            binding.searchText.text = "Search news for title: $searchQuery"
            searchNewsByTitle(searchQuery)
        }


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


    }

    private fun searchNewsByTitle(query: String) {
        binding.loadingProgress.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.IO) {
            val response = NewsApi.searchNewsByTitle(query)

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