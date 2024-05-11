package com.example.githubuser.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.databinding.FragmentSettingBinding
import com.example.githubuser.helper.SettingModelFactory
import com.example.githubuser.helper.SettingPreferences
import com.example.githubuser.view.SettingViewModel

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = SettingPreferences.getInstance(requireContext().dataStore)
        val settingViewModel =
            ViewModelProvider(this, SettingModelFactory(preferences))[SettingViewModel::class.java]

        settingViewModel.getThemeSetting()
            .observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
                setDarkMode(isDarkModeActive)
            }

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            settingViewModel.saveThemeSetting(isChecked)
        }
    }

    private fun setDarkMode(isDarkModeActive: Boolean) {
        if (isDarkModeActive) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.switchTheme.isChecked = true
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.switchTheme.isChecked = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}