package com.example.newsapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.adapters.ArticleAdapter
import com.example.newsapp.adapters.ItemAdapterType
import com.example.newsapp.databinding.FragmentDailyNewsBinding
import com.example.newsapp.retrofit.Repository
import com.example.newsapp.viewmodel.NewsApiViewModel
import com.example.newsapp.viewmodel.NewsApiViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class DailyNewsFragment : Fragment() {

    private lateinit var binding: FragmentDailyNewsBinding

    private val newsApiViewModel by viewModels<NewsApiViewModel>(
        factoryProducer = { NewsApiViewModelFactory(Repository()) }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDailyNewsBinding.inflate(inflater, container, false)

        val topStoriesAdapter = ArticleAdapter(ItemAdapterType.TOP)
        val linSnapHelper = LinearSnapHelper()

        with(binding.topStoriesRecyclerView) {
            linSnapHelper.attachToRecyclerView(this)
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = topStoriesAdapter
        }

        lifecycleScope.launch(Dispatchers.IO) {
            newsApiViewModel.topStoriesFlow.collectLatest {
                topStoriesAdapter.submitData(it)
            }
        }

        val lastNewsAdapter = ArticleAdapter(ItemAdapterType.LAST)

        with(binding.latestNewsRecyclerView) {
            val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)

            addItemDecoration(dividerItemDecoration)
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = lastNewsAdapter
        }

        lifecycleScope.launch(Dispatchers.IO) {
            newsApiViewModel.topStoriesFlow.collectLatest {
                lastNewsAdapter.submitData(it)
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = DailyNewsFragment()
    }

}