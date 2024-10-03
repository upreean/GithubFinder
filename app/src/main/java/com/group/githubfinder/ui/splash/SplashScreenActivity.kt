package com.group.githubfinder.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.group.githubfinder.MainActivity
import com.group.githubfinder.R
import com.group.githubfinder.ui.theme.SettingPreferences
import com.group.githubfinder.ui.theme.ThemeViewModel
import com.group.githubfinder.ui.theme.ViewModelFactory
import com.group.githubfinder.ui.theme.dataStore

class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_DELAY: Long = 2000 // Delay dalam milidetik

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val themeViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            ThemeViewModel::class.java
        )

        // Mengamati perubahan tema
        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                // Set tema gelap
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // Set tema terang
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            // Handler untuk menunda memulai MainActivity setelah mengatur tema
            Handler().postDelayed({
                // Intent untuk memulai MainActivity
                val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(intent)

                // Menutup SplashScreenActivity setelah memulai MainActivity
                finish()
            }, SPLASH_DELAY)
        }
    }
}