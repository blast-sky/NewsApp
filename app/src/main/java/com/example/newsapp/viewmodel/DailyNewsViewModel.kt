package com.example.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.newsapp.model.Article
import com.example.newsapp.paging.NewsPagingSource
import com.example.newsapp.retrofit.RetrofitInstance

class DailyNewsViewModel : ViewModel() {

    private val pageSize = 20

    val topHeadlines = Pager(
        PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { NewsPagingSource(::loadTopHeadlines, pageSize) }
    ).liveData.cachedIn(viewModelScope)

    val latestItNews = Pager(
        PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            NewsPagingSource(
                { page, pageSize ->
                    loadLatestNews("it", page, pageSize)
                },
                pageSize
            )
        }
    ).liveData.cachedIn(viewModelScope)

    val latestBitcoinNews = Pager(
        PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            NewsPagingSource(
                { page, pageSize ->
                    loadLatestNews("bitcoin", page, pageSize)
                },
                pageSize
            )
        }
    ).liveData.cachedIn(viewModelScope)

    private suspend fun loadTopHeadlines(page: String, size: String): List<Article> {
        val response = RetrofitInstance.newsService.topHeadlines(
            country = "ru",
            page = page,
            pageSize = size
        )
        return response.body()!!.articles
    }

    private suspend fun loadLatestNews(filter: String, page: String, size: String): List<Article> {
        val response = RetrofitInstance.newsService.everything(
            filter = filter,
            page = page,
            pageSize = size
        )
        return response.body()!!.articles
    }
}

//class NewsApiViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return DailyNewsViewModel(repository) as? T ?: throw TypeCastException("NewsApiModelFactory")
//    }
//}