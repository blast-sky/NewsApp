package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.newsapp.databinding.DailyNewsLatestNewsItemBinding
import com.example.newsapp.databinding.DailyNewsTopStoriesItemBinding
import com.example.newsapp.model.Article
import com.squareup.picasso.Picasso

enum class ItemAdapterType {
    TOP,
    LAST
}

class ArticleAdapter(
    private val itemType: ItemAdapterType
) : PagingDataAdapter<Article, ArticleAdapter.ViewHolder>(ArticleDiffCallback()) {

    abstract class ViewHolder(
        binding: ViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        abstract fun setArticle(article: Article)
    }

    inner class TopViewHolder(
        private val binding: DailyNewsTopStoriesItemBinding
    ) : ViewHolder(binding) {
        override fun setArticle(article: Article) {
            with(binding) {
                topStoriesAuthorLabel.text = article.author ?: "Не известно"
                topStoriesTitle.text = article.title
                Picasso.get().load(article.urlToImage).into(topStoriesImageView)
            }
        }
    }

    inner class LastViewHolder(
        private val binding: DailyNewsLatestNewsItemBinding
    ) : ViewHolder(binding) {
        override fun setArticle(article: Article) {
            with(binding) {
                newsAuthor.text = article.author ?: "Не известно"
                newsTitle.text = article.title
                Picasso.get().load(article.urlToImage).into(newsImage)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) ?: throw InternalError("No internet")
        holder.setArticle(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when(itemType) {
            ItemAdapterType.TOP ->
                TopViewHolder(DailyNewsTopStoriesItemBinding.inflate(inflater, parent, false))
            ItemAdapterType.LAST ->
                LastViewHolder(DailyNewsLatestNewsItemBinding.inflate(inflater, parent, false))
        }
    }

}

class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {

    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.source == newItem.source && oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

}