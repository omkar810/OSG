package com.example.omshivgoraksha.data.local

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "app_preferences"
)

class SessionManager(
    private val context: Context
) {

    companion object {

        private val ACCESS_TOKEN =
            stringPreferencesKey("access_token")
    }

    val accessToken: Flow<String?> =
        context.dataStore.data.map { preferences ->

            preferences[ACCESS_TOKEN]
        }

    suspend fun saveAccessToken(
        token: String
    ) {

        context.dataStore.edit { preferences ->

            preferences[ACCESS_TOKEN] = token
        }
    }

    suspend fun clearSession() {

        context.dataStore.edit { preferences ->

            preferences.clear()
        }
    }
}