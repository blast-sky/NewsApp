package com.example.newsapp.ui.adapter.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.LoadstateViewholderBinding

class NewsLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<NewsLoadStateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            LoadstateViewholderBinding.inflate(inflater, parent, false),
            retry
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) = holder.bind(loadState)

    class ViewHolder(
        private val binding: LoadstateViewholderBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init { binding.loadStateButton.setOnClickListener { retry.invoke() } }

        fun bind(loadState: LoadState) {
            with(binding) {
                if (loadState is LoadState.Error)
                    loadStateText.text = loadState.error.localizedMessage
                loadStateProgressBar.visibility = toVisibility(loadState is LoadState.Loading)
                loadStateButton.visibility = toVisibility(loadState !is LoadState.Loading)
                loadStateText.visibility = toVisibility(loadState !is LoadState.Loading)
            }
        }

        private fun toVisibility(constraint: Boolean): Int = if (constraint) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}