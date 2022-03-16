package com.example.newsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.newsapp.bookmarks.DatabaseRepository
import com.example.newsapp.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookmarksViewModel(private val databaseRepository: DatabaseRepository) : ViewModel() {

    val articles = MutableLiveData<MutableList<Article>>(mutableListOf())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            articles.postValue(databaseRepository.getAllArticles().toMutableList())
        }
    }

    fun deleteArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        databaseRepository.deleteArticle(article)
        articles.value?.remove(article)
    }
}

class BookmarksViewModelFactory(
    private val databaseRepository: DatabaseRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BookmarksViewModel(databaseRepository) as T
    }
}