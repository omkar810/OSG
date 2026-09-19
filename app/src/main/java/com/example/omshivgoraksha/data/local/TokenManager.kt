//package com.example.omshivgoraksha.data.local
//
//import android.content.Context
//import androidx.datastore.preferences.core.edit
//import androidx.datastore.preferences.core.stringPreferencesKey
//import androidx.datastore.preferences.preferencesDataStore
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
//
//private val Context.authDataStore by preferencesDataStore(
//    name = "auth_preferences"
//)
//
//class TokenManager(
//    private val context: Context
//) {
//
//    companion object {
//
//        private val JWT_TOKEN =
//            stringPreferencesKey("jwt_token")
//    }
//
//    val token: Flow<String?> =
//        context.authDataStore.data.map { preferences ->
//            preferences[JWT_TOKEN]
//        }
//
//    suspend fun saveToken(token: String) {
//
//        context.authDataStore.edit { preferences ->
//
//            preferences[JWT_TOKEN] = token
//        }
//    }
//
//    suspend fun clearToken() {
//
//        context.authDataStore.edit { preferences ->
//
//            preferences.remove(JWT_TOKEN)
//        }
//    }
//}

package com.example.omshivgoraksha.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.omshivgoraksha.data.remote.JwtUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.authDataStore by preferencesDataStore(
    name = "auth_preferences"
)

class TokenManager(
    private val context: Context
) {

    companion object {

        private val JWT_TOKEN =
            stringPreferencesKey("jwt_token")
    }

    val token: Flow<String?> =
        context.authDataStore.data.map { preferences ->
            preferences[JWT_TOKEN]
        }

    suspend fun saveToken(token: String) {

        context.authDataStore.edit { preferences ->

            preferences[JWT_TOKEN] = token
        }
    }

    suspend fun getToken(): String? {

        return token.first()
    }

    suspend fun isTokenValid(): Boolean {

        val storedToken = getToken()

        return JwtUtils.isTokenValid(
            storedToken
        )
    }

    suspend fun clearToken() {

        context.authDataStore.edit { preferences ->

            preferences.remove(JWT_TOKEN)
        }
    }
}