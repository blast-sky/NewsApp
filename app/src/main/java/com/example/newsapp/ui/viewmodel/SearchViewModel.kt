package com.example.newsapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.Article
import com.example.newsapp.data.retrofit.NewsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel(
    private val newsService: NewsService
) : ViewModel() {

    val searchedArticles = MutableLiveData<List<Article>>()

    fun search(query: String?) {
        if (query == null || query == "") {
            searchedArticles.postValue(listOf())
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = newsService.everything(query)
                searchedArticles.postValue(response.body()?.articles ?: listOf())
            } catch (e: Exception) {
                searchedArticles.postValue(listOf())
            }
        }
    }

    class Factory @Inject constructor(
        private val newsService: NewsService
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SearchViewModel(newsService) as T
        }
    }

}