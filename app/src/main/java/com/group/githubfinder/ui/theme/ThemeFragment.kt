package com.group.githubfinder.ui.theme

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.switchmaterial.SwitchMaterial
import com.group.githubfinder.R

class ThemeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_theme, container, false)

        val switchTheme = view.findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(requireActivity().application.dataStore)
        val themeViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            ThemeViewModel::class.java
        )

        themeViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            themeViewModel.saveThemeSetting(isChecked)
        }

        return view
    }
}