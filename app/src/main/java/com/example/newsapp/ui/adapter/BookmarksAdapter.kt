package com.example.newsapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.ui.adapter.paging.OnItemClickListener
import com.example.newsapp.databinding.VerticalNewsItemBinding
import com.example.newsapp.domain.model.Article

class BookmarksAdapter(
    private var articles: List<Article> = listOf()
) : RecyclerView.Adapter<VerticalViewHolder>() {

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(articles: List<Article>) {
        this.articles = articles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return VerticalViewHolder(VerticalNewsItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: VerticalViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
        holder.setOnClickListener { listener?.invoke(article) }
    }

    override fun getItemCount(): Int = articles.size
}