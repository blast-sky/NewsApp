package com.example.newsapp.model

import java.io.Serializable
import java.util.*

data class Articles(
    val articles: List<Article>
)

data class Article (
    val source: Source,
    val author: String? = null,
    val title: String,
    val description: String? = null,
    val url: String,
    val urlToImage: String? = null,
    val publishedAt: Date,
    val content: String? = null
) : Serializable

data class Source (
    val id: String? = null,
    val name: String
)