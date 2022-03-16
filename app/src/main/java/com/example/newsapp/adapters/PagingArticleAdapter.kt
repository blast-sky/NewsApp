package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.newsapp.R
import com.example.newsapp.databinding.DailyNewsLatestNewsItemBinding
import com.example.newsapp.databinding.DailyNewsTopStoriesItemBinding
import com.example.newsapp.model.Article
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collectLatest

enum class ItemAdapterType {
    TOP,
    LAST
}

typealias OnItemClickListener = (Article) -> Unit

class PagingArticleAdapter(
    private val itemType: ItemAdapterType
) : PagingDataAdapter<Article, PagingArticleAdapter.ViewHolder>(ArticleDiffCallback()) {

    abstract class ViewHolder(
        private val binding: ViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        abstract fun setArticle(article: Article)
        fun setOnClickListener(listener: () -> Unit) {
            binding.root.setOnClickListener { listener.invoke() }
        }
    }

    inner class TopViewHolder(
        private val binding: DailyNewsTopStoriesItemBinding
    ) : ViewHolder(binding) {
        override fun setArticle(article: Article) {
            with(binding) {
                topStoriesAuthorLabel.text =
                    article.source.name ?: R.string.without_author.toString()
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
                newsAuthor.text = article.source.name ?: R.string.without_author.toString()
                newsTitle.text = article.title
                Picasso.get().load(article.urlToImage).into(newsImage)
            }
        }
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.setArticle(item)
        holder.setOnClickListener { listener?.invoke(item) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (itemType) {
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