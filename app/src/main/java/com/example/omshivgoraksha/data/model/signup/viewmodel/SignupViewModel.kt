package com.example.omshivgoraksha.data.model.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omshivgoraksha.data.model.auth.SignupRequest
import com.example.omshivgoraksha.data.remote.RetrofitClient
import com.example.omshivgoraksha.data.repository.SignupRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class SignupUiState {

    data object Idle : SignupUiState()

    data object Loading : SignupUiState()

    data class Success(
        val message: String
    ) : SignupUiState()

    data class Error(
        val message: String
    ) : SignupUiState()
}

class SignupViewModel : ViewModel() {

    private val repository =
        SignupRepository(RetrofitClient.apiService)

    private val _uiState =
        MutableStateFlow<SignupUiState>(SignupUiState.Idle)

    val uiState: StateFlow<SignupUiState> =
        _uiState.asStateFlow()

    fun signupUser(request: SignupRequest) {

        viewModelScope.launch {

            _uiState.value =
                SignupUiState.Loading

            val result =
                repository.signupUser(request)

            result
                .onSuccess { message ->

                    _uiState.value =
                        SignupUiState.Success(message)
                }
                .onFailure { exception ->

                    _uiState.value =
                        SignupUiState.Error(
                            exception.message
                                ?: "Signup failed."
                        )
                }
        }
    }

    fun resetState() {
        _uiState.value =
            SignupUiState.Idle
    }
}