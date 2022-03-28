package com.example.newsapp.ui.adapter.paging

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.HorizontalNewsItemBinding
import com.example.newsapp.model.Article
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

fun <T : Any, V : RecyclerView.ViewHolder> PagingDataAdapter<T, V>.withLoadStateAdapters(
    header: NewsLoadStateAdapter,
    footer: NewsLoadStateAdapter
): ConcatAdapter {
    addLoadStateListener { loadStates ->
        header.loadState = loadStates.refresh
        footer.loadState = when (loadStates.refresh) {
            is LoadState.NotLoading -> loadStates.append
            else -> loadStates.refresh
        }
    }
    return ConcatAdapter(header, this, footer)
}

typealias OnItemClickListener = (Article) -> Unit

class PagingArticleAdapter :
    PagingDataAdapter<Article, PagingArticleAdapter.TopViewHolder>(ArticleDiffCallback()) {

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class TopViewHolder(
        private val binding: HorizontalNewsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setOnClickListener(listener: () -> Unit) {
            binding.root.setOnClickListener { listener.invoke() }
        }

        @SuppressLint("SimpleDateFormat")
        fun setArticle(article: Article) {
            with(binding) {
                topStoriesAuthorLabel.text = article.source.name
                topStoriesTitle.text = article.title
                val dateFormat = SimpleDateFormat("dd MMMM yyyy")
                val strDate = dateFormat.format(article.publishedAt)
                topStoriesDateLabel.text = strDate
                if (article.urlToImage == null || article.urlToImage == "") {
                    topStoriesImageView.scaleType = ImageView.ScaleType.CENTER
                    topStoriesImageView.setImageResource(R.drawable.ic_image)
                } else {
                    Picasso.get()
                        .load(article.urlToImage)
                        .into(topStoriesImageView)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: TopViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.setArticle(item)
        holder.setOnClickListener { listener?.invoke(item) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TopViewHolder(HorizontalNewsItemBinding.inflate(inflater, parent, false))
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