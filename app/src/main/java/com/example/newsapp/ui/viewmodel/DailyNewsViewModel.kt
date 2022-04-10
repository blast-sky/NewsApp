package com.example.newsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.newsapp.model.Article
import com.example.newsapp.repository.RetrofitRepository
import com.example.newsapp.ui.adapter.paging.NewsPagingSource
import javax.inject.Inject

class DailyNewsViewModel(
    private val retrofitRepository: RetrofitRepository
) : ViewModel() {

    private val pageSize = 6

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

    private suspend fun loadTopHeadlines(page: Int, pageSize: Int): List<Article> {
        val response = retrofitRepository.getTopHeadlines(
            page = page,
            pageSize = pageSize
        )
        return response.body()!!.articles
    }

    private suspend fun loadLatestNews(filter: String, page: Int, pageSize: Int): List<Article> {
        val response = retrofitRepository.getEverything(
            filter = filter,
            page = page,
            pageSize = pageSize
        )
        return response.body()!!.articles
    }

    class Factory @Inject constructor(
        private val retrofitRepository: RetrofitRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DailyNewsViewModel(retrofitRepository) as T
        }
    }
}