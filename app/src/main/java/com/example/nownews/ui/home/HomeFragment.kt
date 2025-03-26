package com.example.nownews.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nownews.R
import com.example.nownews.adapter.NewsAdapter
import com.example.nownews.api.NewsApi.getTrendingNews
import com.example.nownews.databinding.FragmentHomeBinding
import com.example.nownews.model.NewsArticle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private val newsList = mutableListOf<NewsArticle>()
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        newsAdapter = NewsAdapter(newsList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = newsAdapter

        // Call the function in the background
        fetchTrendingNews()
    }

    private fun fetchTrendingNews() {
        // Using lifecycleScope for automatic cleanup
        lifecycleScope.launch(Dispatchers.IO) {
            val response = getTrendingNews()

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