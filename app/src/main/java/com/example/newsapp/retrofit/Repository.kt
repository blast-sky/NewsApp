package com.example.newsapp.retrofit

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.model.Article
import com.example.newsapp.model.NewsApiResponse
import com.example.newsapp.paging.ArticleLoader
import com.example.newsapp.paging.NewsPagingSource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

private const val API_KEY = "962dc8a491734f0b9b822724c04b8eca"

class Repository {

    fun getPagedArticles(pageSize: Int): Flow<PagingData<Article>> {
        val loader: ArticleLoader = { page: String, size: String ->
            val response = getTopStories(
                page = page,
                pageSize = size
            )
            response.body()!!.articles
        }
        return Pager(
            PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsPagingSource(loader, pageSize) }
        ).flow
    }

    suspend fun getTopStories(
        country: String = "ru",
        pageSize: String = "100",
        page: String = "1",
        apiKey: String = API_KEY
    ): Response<NewsApiResponse> {
        return RetrofitInstance.newsApi.getTopStories(country, pageSize, page, apiKey)
    }

    suspend fun getLatestNews(
        filter: String = "it",
        pageSize: String = "100",
        page: String = "1",
        apiKey: String = API_KEY
    ): Response<NewsApiResponse> {
        return RetrofitInstance.newsApi.getLatestNews(filter, pageSize, page, apiKey)
    }

}