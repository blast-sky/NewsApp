package com.example.newsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DatabaseViewModel(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    val allArticles = databaseRepository.getAllArticles()

    suspend fun searchArticles(query: String): List<Article> {
        return databaseRepository.searchArticlesWithTitleAndSourceName(query).map { it.toArticle() }
    }

    fun insertArticle(article: Article) = viewModelScope.launch((Dispatchers.IO)) {
        databaseRepository.insertArticle(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        databaseRepository.deleteArticleByUrl(article.url)
    }

    class Factory @Inject constructor(
        private val databaseRepository: DatabaseRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DatabaseViewModel(databaseRepository) as T
        }
    }
}