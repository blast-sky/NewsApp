package com.example.newsapp.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.R
import com.example.newsapp.Settings
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.ui.fragments.BookmarksFragment
import com.example.newsapp.ui.fragments.DailyNewsFragment

val Context.settings: Settings
    get() = when (this) {
        is MainActivity -> settings
        else -> applicationContext.settings
    }

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var settings: Settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        settings = Settings(this)

        settings.setNightMode(settings.isNightModeActive())

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.dailyNewsFragment,
                R.id.bookmarksFragment,
                R.id.settingsFragment
            )
        )

        navController.addOnDestinationChangedListener { _, _, args ->
            args?.getString("fromFragment")?.let {
                when (it) {
                    DailyNewsFragment::class.toString() -> binding.bottomNavigation.menu.getItem(0).isChecked =
                        true
                    BookmarksFragment::class.toString() -> binding.bottomNavigation.menu.getItem(1).isChecked =
                        true
                }
            }
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigation.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}