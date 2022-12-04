package com.example.newsapp.data.room

import androidx.room.*
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.model.Source
import java.util.*

@[TypeConverters(DateConverter::class) Entity(
    indices = [
        Index("sourceName"),
        Index("title")
    ],
    tableName = "articles",
)]
data class ArticleDbEntity(
    val sourceName: String,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: Date,
    val content: String?,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
) {

    constructor(article: Article) : this(
        sourceName = article.source.name,
        author = article.author,
        title = article.title,
        description = article.description,
        url = article.url,
        urlToImage = article.urlToImage,
        publishedAt = article.publishedAt,
        content = article.content
    )

    fun toArticle() = Article(
        source = Source(null, sourceName),
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content
    )
}