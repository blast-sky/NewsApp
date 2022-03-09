package com.example.newsapp.retrofit

import com.example.newsapp.model.NewsApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getTopStories(
        @Query("country") country: String,
        @Query("pageSize") pageSize: String,
        @Query("page") page: String,
        @Query("apiKey") apiKey: String
    ) : Response<NewsApiResponse>

    @GET("everything")
    suspend fun getLatestNews(
        @Query("q") filter: String,
        @Query("pageSize") pageSize: String,
        @Query("page") page: String,
        @Query("apiKey") apiKey: String
    ) : Response<NewsApiResponse>

}