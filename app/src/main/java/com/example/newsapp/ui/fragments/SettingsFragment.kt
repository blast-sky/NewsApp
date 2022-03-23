package com.example.newsapp.ui.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.newsapp.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}