package com.example.newsapp.bookmarks

import com.example.newsapp.model.Article

class DatabaseRepository(private val articleDao: ArticleDao) {

    suspend fun getAllArticles(): List<Article> = articleDao.getAllArticles().map { it.toArticle() }

    suspend fun insertArticle(article: Article) = articleDao.insertArticle(ArticleDbEntity(article))

    suspend fun deleteArticle(article: Article) = articleDao.deleteArticle(ArticleDbEntity(article))
}