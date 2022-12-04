package com.example.newsapp.ui

import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.example.newsapp.R

class Settings constructor(activity: MainActivity) {

    private val delegate = activity.delegate
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
    private val resources = activity.resources

    fun isTopHeadlinesActive() =
        sharedPreferences.getBoolean(resources.getString(R.string.is_top_news_active), true)

    fun isItActive() =
        sharedPreferences.getBoolean(resources.getString(R.string.is_it_news_active), true)

    fun isBitcoinActive() =
        sharedPreferences.getBoolean(resources.getString(R.string.is_bitcoin_news_active), true)

    fun isNightModeActive() =
        sharedPreferences.getBoolean(resources.getString(R.string.is_night_mode), false)

    fun setNightMode(flag: Boolean) {
        AppCompatDelegate.setDefaultNightMode(boolToNightMode(flag))
        delegate.applyDayNight()
    }

    private fun boolToNightMode(boolean: Boolean) = when (boolean) {
        true -> AppCompatDelegate.MODE_NIGHT_YES
        false -> AppCompatDelegate.MODE_NIGHT_NO
    }
}