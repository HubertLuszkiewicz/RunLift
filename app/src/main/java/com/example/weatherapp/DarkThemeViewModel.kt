package com.example.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DarkThemeViewModel : ViewModel() {
    private val _isDark = MutableLiveData(false)
    val isDark: LiveData<Boolean> = _isDark

    fun updateIsDark() {
        _isDark.value = !_isDark.value!!
    }
}