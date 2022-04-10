package com.example.newsapp.repository

import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.example.newsapp.model.Article
import com.example.newsapp.room.ArticleDao
import com.example.newsapp.room.ArticleDbEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatabaseRepository @Inject constructor(
    private val articleDao: ArticleDao
) {

    fun getAllArticles() = articleDao.getAllArticles()
        .asFlow()
        .map { list -> list.map { entity -> entity.toArticle() } }
        .flowOn(Dispatchers.IO)
        .asLiveData()

    suspend fun isArticleContained(article: Article): Boolean {
        val response = articleDao.searchArticle(article.title, article.source.name)
        return response != null
    }

    suspend fun searchArticlesWithTitleAndSourceName(query: String) =
        articleDao.searchArticlesWithTitleAndSourceName(query)

    suspend fun insertArticle(article: Article) = articleDao.insertArticle(ArticleDbEntity(article))

    suspend fun deleteArticle(article: Article) = articleDao.deleteArticle(ArticleDbEntity(article))

    suspend fun deleteArticleByUrl(url: String) = articleDao.deleteArticleByUrl(url)
}