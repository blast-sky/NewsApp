package com.example.newsapp.di

import com.example.newsapp.data.retrofit.NewsService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

private const val BASE_URL = "https://newsapi.org/"
private const val API_KEY_HEADER = "X-Api-Key"

@Module
class NetworkModule {

    @[Provides Singleton]
    fun provideOkHttpClient(@NewsApiQualifier apiKey: String): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(API_KEY_HEADER, apiKey)
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    @[Provides Singleton]
    fun provideNewsService(okHttpClient: OkHttpClient): NewsService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsService::class.java)
    }
}

@Qualifier
annotation class NewsApiQualifier