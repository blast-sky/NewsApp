package com.example.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.ui.adapter.paging.OnItemClickListener
import com.example.newsapp.databinding.VerticalNewsItemBinding
import com.example.newsapp.model.Article

class SearchAdapter(
    var articles: List<Article> = listOf()
) : RecyclerView.Adapter<VerticalViewHolder>() {

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VerticalNewsItemBinding.inflate(inflater, parent, false)
        return VerticalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VerticalViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
        holder.setOnClickListener { listener?.invoke(article) }
    }

    override fun getItemCount(): Int = articles.size


}