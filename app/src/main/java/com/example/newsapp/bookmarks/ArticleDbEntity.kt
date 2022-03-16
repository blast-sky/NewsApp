package com.example.newsapp.bookmarks

import androidx.room.*
import com.example.newsapp.model.Article
import com.example.newsapp.model.Source
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(
    indices = [
        Index("sourceName"),
        Index("title")
    ],
    tableName = "articles",
)
@TypeConverters(DateConverter::class)
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
        article.source.name,
        article.author,
        article.title,
        article.description,
        article.url,
        article.urlToImage,
        article.publishedAt,
        article.content
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

class DateConverter {
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}