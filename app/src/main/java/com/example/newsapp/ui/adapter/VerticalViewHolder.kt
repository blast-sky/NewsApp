package com.example.newsapp.ui.adapter

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.VerticalNewsItemBinding
import com.example.newsapp.model.Article
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

class VerticalViewHolder(
    private val binding: VerticalNewsItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun setOnClickListener(listener: () -> Unit) {
        binding.root.setOnClickListener { listener.invoke() }
    }

    @SuppressLint("SimpleDateFormat")
    fun bind(article: Article) {
        with(binding) {
            newsAuthor.text = article.source.name
            newsTitle.text = article.title
            val dateFormat = SimpleDateFormat("dd MMMM yyyy")
            val strDate = dateFormat.format(article.publishedAt)
            dateLabel.text = strDate
            if (article.urlToImage == null) {
                newsImage.scaleType = ImageView.ScaleType.CENTER
                newsImage.setImageResource(R.drawable.ic_image)
            } else {
                Picasso.get()
                    .load(article.urlToImage)
                    .error(R.drawable.ic_image)
                    .into(newsImage)
            }
        }
    }
}