package com.example.newsapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.PagingArticleAdapter
import com.example.newsapp.adapters.withLoadStateAdapters
import com.example.newsapp.appComponent
import com.example.newsapp.ui.viewmodel.DailyNewsViewModel
import com.example.newsapp.databinding.FragmentDailyNewsBinding
import com.example.newsapp.ui.adapter.paging.NewsLoadStateAdapter
import com.example.newsapp.ui.viewmodel.DatabaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DailyNewsFragment : Fragment() {

    @Inject
    lateinit var dnFactory: DailyNewsViewModel.Factory
    private val dailyNewsViewModel by viewModels<DailyNewsViewModel> { dnFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDailyNewsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onAttach(context: Context) {
        context.appComponent().inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentDailyNewsBinding.bind(view)
        super.onViewCreated(binding.root, savedInstanceState)

        val adapters = List(3) { PagingArticleAdapter() }
        val recyclerViews = listOf(
            binding.topStoriesRecyclerView,
            binding.latestItNewsRecyclerView,
            binding.latestBitcoinNewsRecyclerView
        )

        setupAdapters(adapters, recyclerViews)
        observeAdapters(adapters)
        setupSwipeLayout(adapters, recyclerViews, binding)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_dummy_menu, menu)

        menu.findItem(R.id.search_item).setOnMenuItemClickListener {
            findNavController().navigate(
                R.id.action_dailyNewsFragment_to_searchFragment,
                Bundle().apply { putString("fromFragment", DailyNewsFragment::class.toString()) }
            )
            return@setOnMenuItemClickListener true
        }
    }

    private fun setupSwipeLayout(
        adapters: List<PagingArticleAdapter>,
        recyclerViews: List<RecyclerView>,
        binding: FragmentDailyNewsBinding
    ) {
        binding.swipeLayout.setOnRefreshListener {
            for (adapter in adapters)
                adapter.refresh()
            for (recycler in recyclerViews)
                recycler.scrollToPosition(0)
            binding.swipeLayout.isRefreshing = false
        }
    }

    private fun observeAdapters(adapters: List<PagingArticleAdapter>) {
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
    }

    private fun setupAdapters(
        adapters: List<PagingArticleAdapter>,
        recyclerViews: List<RecyclerView>
    ) {
        for (adapter in adapters) {
            adapter.setOnItemClickListener { article ->
                findNavController().navigate(
                    R.id.action_dailyNewsFragment_to_articleFragment,
                    Bundle().apply {
                        putSerializable("article", article)
                        putString("fromFragment", DailyNewsFragment::class.toString())
                    }
                )
            }
        }

        for ((index, recycler) in recyclerViews.withIndex()) {
            val linSnapHelper = LinearSnapHelper()
            with(recycler) {
                val curAdapter = adapters[index]
                linSnapHelper.attachToRecyclerView(this)
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                adapter = curAdapter.withLoadStateAdapters(
                    NewsLoadStateAdapter { curAdapter.retry() },
                    NewsLoadStateAdapter { curAdapter.retry() }
                )
            }
        }
    }
}