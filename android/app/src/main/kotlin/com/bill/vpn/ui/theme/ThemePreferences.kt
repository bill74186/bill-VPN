package com.bill.vpn.ui.theme

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class ThemeMode {
    Light,
    Dark,
    System
}

class ThemePreferences(context: Context) {
    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _themeMode = MutableStateFlow(loadThemeMode())
    val themeMode: StateFlow<ThemeMode> = _themeMode

    fun setThemeMode(mode: ThemeMode) {
        prefs.edit().putString(KEY_THEME_MODE, mode.name).apply()
        _themeMode.value = mode
    }

    private fun loadThemeMode(): ThemeMode {
        val name = prefs.getString(KEY_THEME_MODE, ThemeMode.System.name) ?: ThemeMode.System.name
        return runCatching { ThemeMode.valueOf(name) }.getOrDefault(ThemeMode.System)
    }

    companion object {
        private const val PREFS_NAME = "bill_vpn_prefs"
        private const val KEY_THEME_MODE = "theme_mode"
    }
}

val LocalThemePreferences = staticCompositionLocalOf<ThemePreferences> {
    error("ThemePreferences not provided")
}

@Composable
fun rememberThemeMode(): ThemeMode {
    val themePrefs = LocalThemePreferences.current
    return themePrefs.themeMode.collectAsState().value
}
