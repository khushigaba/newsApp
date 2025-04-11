package com.example.nownews.ui.comments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.nownews.MainActivity
import kotlinx.coroutines.launch

class CommentsFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Access the database
        val db = (requireActivity() as MainActivity).database

        // Example: Fetch comments for an article
        lifecycleScope.launch {
            db.commentDao().getCommentsForArticle("article123").collect { comments ->
                // Update RecyclerView
            }
        }
    }
}