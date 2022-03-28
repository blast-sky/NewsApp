package com.example.newsapp.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.appComponent
import com.example.newsapp.ui.adapter.SearchAdapter
import com.example.newsapp.ui.viewmodel.SearchViewModel
import com.example.newsapp.databinding.FragmentSearchBinding
import javax.inject.Inject

class SearchFragment : Fragment(), SearchView.OnQueryTextListener {

    @Inject
    lateinit var factory: SearchViewModel.Factory
    private val searchViewModel: SearchViewModel by viewModels { factory }

    private val args: SearchFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSearchBinding.bind(view)

        val searchAdapter = SearchAdapter(listOf()).apply {
            setOnItemClickListener { article ->
                findNavController().navigate(
                    R.id.action_searchFragment_to_articleFragment,
                    Bundle().apply {
                        putSerializable("article", article)
                        putString("fromFragment", args.fromFragment)
                    }
                )
            }
        }

        searchViewModel.searchedArticles.observe(viewLifecycleOwner) { articles ->
            searchAdapter.articles = articles
            searchAdapter.notifyDataSetChanged()
        }

        with(binding.searchRecyclerView) {
            val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
            addItemDecoration(dividerItemDecoration)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = searchAdapter
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val search = menu.findItem(R.id.search_menu_item)
        val searchView = search.actionView as SearchView

        searchView.isIconified = false
        searchView.isSubmitButtonEnabled = false
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchViewModel.search(newText)
        return true
    }
}