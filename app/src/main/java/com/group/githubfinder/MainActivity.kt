package com.group.githubfinder

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.group.githubfinder.databinding.ActivityMainBinding
import com.group.githubfinder.ui.theme.SettingPreferences
import com.group.githubfinder.ui.theme.ThemeViewModel
import com.group.githubfinder.ui.theme.ViewModelFactory
import com.group.githubfinder.ui.theme.dataStore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set tema berdasarkan preferensi yang tersimpan
        val pref = SettingPreferences.getInstance(application.dataStore)
        val themeViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            ThemeViewModel::class.java
        )

        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                // Set tema gelap
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // Set tema terang
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_favorite, R.id.navigation_theme
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}
