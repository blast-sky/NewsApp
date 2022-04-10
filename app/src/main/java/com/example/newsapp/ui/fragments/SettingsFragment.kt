package com.example.newsapp.ui.fragments

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.newsapp.R
import com.example.newsapp.ui.settings

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        if(preference.key == resources.getString(R.string.is_night_mode))
            context?.settings?.setNightMode(context!!.settings.isNightModeActive())
        return super.onPreferenceTreeClick(preference)
    }
}