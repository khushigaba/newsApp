package com.example.nownews.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nownews.R
import com.example.nownews.adapter.NewsCategoryAdapter
import com.example.nownews.databinding.FragmentCategoryBinding
import com.example.nownews.model.NewsCategory

class CategoryFragment : Fragment() {

    private lateinit var adapter: NewsCategoryAdapter
    private lateinit var binding: FragmentCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categories = NewsCategory.entries
        adapter = NewsCategoryAdapter(categories) { category ->
            val bundle = Bundle().apply {
                putString("categoryName", category.category)
            }
            findNavController().navigate(R.id.action_navigation_category_to_viewNewsListFragment, bundle)
        }

        binding.newsCategoryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.newsCategoryRecyclerView.adapter = adapter
        binding.newsCategoryRecyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    }

}