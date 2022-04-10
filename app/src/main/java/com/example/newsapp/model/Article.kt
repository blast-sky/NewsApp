package com.example.newsapp.model

import java.io.Serializable
import java.util.*

data class Article(
    val source: Source,
    val author: String? = null,
    val title: String,
    val description: String? = null,
    val url: String,
    val urlToImage: String? = null,
    val publishedAt: Date,
    val content: String? = null
) : Serializable {

    override fun equals(other: Any?): Boolean {
        (other as? Article)?.let {
            return url == it.url
        }
        return false
    }

    override fun hashCode(): Int {
        return url.hashCode()
    }
}

