package com.example.newsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.Article
import com.example.newsapp.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    val searchedArticles = MutableLiveData<List<Article>>()

    fun search(query: String?) {
        if(query == null || query == "") {
            searchedArticles.postValue(listOf())
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.newsService.everything(query)
            searchedArticles.postValue(response.body()?.articles ?: listOf())
        }
    }

}