package com.example.omshivgoraksha.data.model.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omshivgoraksha.data.local.TokenManager
import com.example.omshivgoraksha.data.remote.RetrofitClient
import com.example.omshivgoraksha.data.repository.SignupRepository
import com.example.omshivgoraksha.data.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class LoginUiState {

    data object Idle : LoginUiState()

    data object Loading : LoginUiState()

    data class Success(
        val message: String
    ) : LoginUiState()

    data class Error(
        val message: String
    ) : LoginUiState()
}

class LoginViewModel(
    private val tokenManager: TokenManager
) : ViewModel() {

    private val repository =
        LoginRepository(
            RetrofitClient.apiService,
            tokenManager
        )

    private val _uiState =
        MutableStateFlow<LoginUiState>(
            LoginUiState.Idle
        )

    val uiState: StateFlow<LoginUiState> =
        _uiState.asStateFlow()

    fun login(
        username: String,
        password: String
    ) {

        viewModelScope.launch {

            _uiState.value =
                LoginUiState.Loading

            val result =
                repository.login(
                    username,
                    password
                )

            result
                .onSuccess { message ->

                    _uiState.value =
                        LoginUiState.Success(
                            message
                        )
                }
                .onFailure { exception ->

                    _uiState.value =
                        LoginUiState.Error(
                            exception.message
                                ?: "Login failed."
                        )
                }
        }
    }

    fun resetState() {
        _uiState.value =
            LoginUiState.Idle
    }
}