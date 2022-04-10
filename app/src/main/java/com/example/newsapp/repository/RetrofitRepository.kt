package com.example.newsapp.repository

import com.example.newsapp.retrofit.NewsService
import javax.inject.Inject

class RetrofitRepository @Inject constructor(
    private val newsService: NewsService
) {

    suspend fun getTopHeadlines(page: Int?, pageSize: Int?) = newsService.topHeadlines(
        country = "ru",
        page = page,
        pageSize = pageSize
    )

    suspend fun getEverything(filter: String, page: Int?, pageSize: Int?) = newsService.everything(
        filter = filter,
        page = page,
        pageSize = pageSize
    )
}