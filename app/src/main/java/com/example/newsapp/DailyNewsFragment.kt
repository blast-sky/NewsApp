package com.example.newsapp

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.adapters.PagingArticleAdapter
import com.example.newsapp.adapters.ItemAdapterType
import com.example.newsapp.databinding.FragmentDailyNewsBinding
import com.example.newsapp.model.Article
import com.example.newsapp.viewmodel.DailyNewsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DailyNewsFragment : Fragment() {

    private val dailyNewsViewModel by viewModels<DailyNewsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDailyNewsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentDailyNewsBinding.bind(view)
        super.onViewCreated(binding.root, savedInstanceState)

        val adapters = List(3) { PagingArticleAdapter(ItemAdapterType.TOP) }
        val recyclerViews = listOf(
            binding.topStoriesRecyclerView,
            binding.latestItNewsRecyclerView,
            binding.latestBitcoinNewsRecyclerView
        )

        for (adapter in adapters) {
            adapter.setOnItemClickListener { article ->
                findNavController().navigate(
                    R.id.action_dailyNewsFragment_to_articleFragment,
                    Bundle().apply {
                        putSerializable("article", article)
                    }
                )
            }
        }

        for ((index, recycler) in recyclerViews.withIndex()) {
            val linSnapHelper = LinearSnapHelper()
            with(recycler) {
                linSnapHelper.attachToRecyclerView(this)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = adapters[index]
            }
        }

        dailyNewsViewModel.topHeadlines.observe(viewLifecycleOwner) {
            lifecycleScope.launch(Dispatchers.Default) {
                adapters[0].submitData(it)
            }
        }

        dailyNewsViewModel.latestItNews.observe(viewLifecycleOwner) {
            lifecycleScope.launch(Dispatchers.Default) {
                adapters[1].submitData(it)
            }
        }

        dailyNewsViewModel.latestBitcoinNews.observe(viewLifecycleOwner) {
            lifecycleScope.launch(Dispatchers.Default) {
                adapters[2].submitData(it)
            }
        }

        binding.swipeLayout.setOnRefreshListener {
            for (adapter in adapters)
                adapter.refresh()
            for (recycler in recyclerViews)
                recycler.scrollToPosition(0)
            binding.swipeLayout.isRefreshing = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_dummy_menu, menu)

        menu.findItem(R.id.search_item).setOnMenuItemClickListener {
            findNavController().navigate(R.id.action_dailyNewsFragment_to_searchFragment)
            return@setOnMenuItemClickListener true
        }
    }
}