package com.example.newsapp

import android.content.Context
import androidx.preference.PreferenceManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Settings @Inject constructor(context: Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val resources = context.resources

    fun isTopHeadlinesActive() =
        sharedPreferences.getBoolean(resources.getString(R.string.is_top_news_active), true)

    fun isItActive() =
        sharedPreferences.getBoolean(resources.getString(R.string.is_it_news_active), true)

    fun isBitcoinActive() =
        sharedPreferences.getBoolean(resources.getString(R.string.is_bitcoin_news_active), true)
}