package com.example.newsapp

import android.app.Application
import android.content.Context
import com.example.newsapp.di.AppComponent
import com.example.newsapp.di.DaggerAppComponent

class NewsApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .apiKey(BuildConfig.NEWS_API_KEY)
            .application(this)
            .build()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is NewsApp -> appComponent
        else -> applicationContext.appComponent
    }