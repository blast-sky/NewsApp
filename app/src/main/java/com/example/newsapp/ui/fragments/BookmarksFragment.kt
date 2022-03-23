package com.example.newsapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.appComponent
import com.example.newsapp.ui.adapter.BookmarksAdapter
import com.example.newsapp.databinding.FragmentBookmarksBinding
import com.example.newsapp.ui.viewmodel.DatabaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookmarksFragment : Fragment(), SearchView.OnQueryTextListener {

    @Inject
    lateinit var factory: DatabaseViewModel.Factory
    private val databaseViewModel by viewModels<DatabaseViewModel> { factory }

    private val bmAdapter = BookmarksAdapter().apply {
        setOnItemClickListener { article ->
            findNavController().navigate(
                R.id.action_bookmarksFragment_to_articleFragment,
                Bundle().apply {
                    putString("fromFragment", BookmarksFragment::class.toString())
                    putSerializable("article", article)
                }
            )
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentBookmarksBinding.bind(view)

        databaseViewModel.allArticles.observe(viewLifecycleOwner) { articles ->
            bmAdapter.setData(articles)
        }

        val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
        binding.bookmarksRecyclerView.apply {
            adapter = bmAdapter
            addItemDecoration(dividerItemDecoration)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val search = menu.findItem(R.id.search_menu_item)
        val searchView = search.actionView as? SearchView

        searchView?.isSubmitButtonEnabled = false
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let { query ->
            if (query == "") {
                databaseViewModel.allArticles.observe(viewLifecycleOwner) { articles ->
                    bmAdapter.setData(articles)
                }
            } else {
                lifecycleScope.launch(Dispatchers.Main) {
                    val data = withContext(Dispatchers.IO) {
                        val request = "%$query%"
                        databaseViewModel.searchArticles(request)
                    }
                    bmAdapter.setData(data)
                }
            }
        }
        return true
    }
}