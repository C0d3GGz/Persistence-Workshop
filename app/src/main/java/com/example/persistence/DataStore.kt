package com.example.persistence

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val PREFERENCES_NAME = "user_preferences"

private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)

class DataStore(context: Context) {

    private val dataStore = context.dataStore

    companion object {
        val ALWAYS_ON_SCREEN_KEY = booleanPreferencesKey("always_on_screen")
        val WORKSHOP_KEY = booleanPreferencesKey("workshop")
    }

    val alwaysOnScreenFlow: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[ALWAYS_ON_SCREEN_KEY] ?: false
        }

    suspend fun setAlwaysOnScreen(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[ALWAYS_ON_SCREEN_KEY] = enabled
        }
    }

    // Workshop

    val workshopFlow: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[WORKSHOP_KEY] ?: false
        }

    suspend fun setWorkshop(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[WORKSHOP_KEY] = enabled
        }
    }
}
