package com.example.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.newsapp.retrofit.Repository

class NewsApiViewModel(private val repository: Repository) : ViewModel() {

    val topStoriesFlow = repository.getPagedArticles(20)

}