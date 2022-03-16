package com.example.newsapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.newsapp.bookmarks.Adapter
import com.example.newsapp.bookmarks.ArticleDatabase
import com.example.newsapp.bookmarks.DatabaseRepository
import com.example.newsapp.databinding.FragmentBookmarksBinding
import com.example.newsapp.viewmodel.BookmarksViewModel
import com.example.newsapp.viewmodel.BookmarksViewModelFactory

class BookmarksFragment : Fragment() {

    private val bookmarksViewModel by viewModels<BookmarksViewModel>(
        factoryProducer = {
            BookmarksViewModelFactory(
                DatabaseRepository((activity as MainActivity).database.articleDao())
                )
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBookmarksBinding.inflate(inflater, container, false)

        val bookmarksAdapter = Adapter(bookmarksViewModel.articles.value!!)

        bookmarksViewModel.articles.observe(viewLifecycleOwner) {
            bookmarksAdapter.notifyDataSetChanged()
        }

        binding.bookmarksRecyclerView.apply {
            val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
            addItemDecoration(dividerItemDecoration)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = bookmarksAdapter
        }

        return binding.root
    }
}