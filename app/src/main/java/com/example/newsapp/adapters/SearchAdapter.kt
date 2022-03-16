package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.SearchRecyclerItemBinding
import com.example.newsapp.model.Article

class SearchAdapter(var articles: List<Article>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    inner class ViewHolder(
        val binding: SearchRecyclerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            with(binding) {
                searchAuthor.text = article.author
                searchDate.text = article.publishedAt.toString()
                searchTitle.text = article.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SearchRecyclerItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount(): Int = articles.size


}