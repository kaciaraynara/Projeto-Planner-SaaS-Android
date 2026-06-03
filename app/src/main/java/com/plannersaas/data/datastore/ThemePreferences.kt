package com.plannersaas.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ThemePreferences(private val context: Context) {

    companion object {
        val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        val THEME_COLOR_KEY = stringPreferencesKey("theme_color")
        val DYNAMIC_COLORS_KEY = booleanPreferencesKey("dynamic_colors")
        val LANGUAGE_KEY = stringPreferencesKey("language")
    }

    val darkModeFlow: Flow<Boolean?> = context.dataStore.data.map { preferences ->
        preferences[DARK_MODE_KEY]
    }

    val themeColorFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[THEME_COLOR_KEY] ?: "Blue"
    }

    val dynamicColorsFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[DYNAMIC_COLORS_KEY] ?: false
    }

    val languageFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[LANGUAGE_KEY] ?: "pt" // Default to Portuguese
    }

    suspend fun saveDarkMode(isDarkMode: Boolean?) {
        context.dataStore.edit { preferences ->
            if (isDarkMode == null) {
                preferences.remove(DARK_MODE_KEY)
            } else {
                preferences[DARK_MODE_KEY] = isDarkMode
            }
        }
    }

    suspend fun saveThemeColor(colorName: String) {
        context.dataStore.edit { preferences ->
            preferences[THEME_COLOR_KEY] = colorName
        }
    }

    suspend fun saveDynamicColors(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DYNAMIC_COLORS_KEY] = enabled
        }
    }

    suspend fun saveLanguage(languageCode: String) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = languageCode
        }
    }
}
