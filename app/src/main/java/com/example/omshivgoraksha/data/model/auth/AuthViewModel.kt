package com.example.omshivgoraksha.data.model.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omshivgoraksha.data.local.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AuthState {

    data object Checking : AuthState()

    data object LoggedIn : AuthState()

    data object LoggedOut : AuthState()
}

class AuthViewModel(
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _authState =
        MutableStateFlow<AuthState>(
            AuthState.Checking
        )

    val authState: StateFlow<AuthState> =
        _authState.asStateFlow()

    init {
        checkAuthentication()
    }

    fun checkAuthentication() {

        viewModelScope.launch {

            val isValid =
                tokenManager.isTokenValid()

            if (isValid) {

                _authState.value =
                    AuthState.LoggedIn

            } else {

                /*
                 * Token is missing or expired.
                 */
                tokenManager.clearToken()

                _authState.value =
                    AuthState.LoggedOut
            }
        }
    }

    fun logout() {

        viewModelScope.launch {

            tokenManager.clearToken()

            _authState.value =
                AuthState.LoggedOut
        }
    }
}