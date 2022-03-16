package com.example.newsapp.bookmarks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.DailyNewsLatestNewsItemBinding
import com.example.newsapp.model.Article
import com.squareup.picasso.Picasso

class Adapter(private val articles: List<Article>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    inner class ViewHolder(
        val binding: DailyNewsLatestNewsItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(DailyNewsLatestNewsItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        with(holder.binding) {
            newsAuthor.text = article.source.name
            newsTitle.text = article.title
            Picasso.get().load(article.urlToImage).into(newsImage)
        }
    }

    override fun getItemCount(): Int = articles.size
}