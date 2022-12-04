package com.example.newsapp.data.retrofit

import com.example.newsapp.domain.model.Articles
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("v2/top-headlines")
    suspend fun topHeadlines(
        @Query("country") country: String,
        @Query("pageSize") pageSize: Int? = null,
        @Query("page") page: Int? = null,
    ): Response<Articles>

    @GET("v2/everything")
    suspend fun everything(
        @Query("q") filter: String,
        @Query("pageSize") pageSize: Int? = null,
        @Query("page") page: Int? = null,
    ): Response<Articles>
}