package com.example.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.ui.fragments.BookmarksFragment
import com.example.newsapp.ui.fragments.DailyNewsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                    DailyNewsFragment::class.toString() -> binding.bottomNavigation.menu.getItem(0).isChecked = true
                    BookmarksFragment::class.toString() -> binding.bottomNavigation.menu.getItem(1).isChecked = true
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