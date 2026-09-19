package com.example.omshivgoraksha.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.omshivgoraksha.R
import com.example.omshivgoraksha.ui.components.PrimaryButton
import com.example.omshivgoraksha.ui.theme.GoldDark
import com.example.omshivgoraksha.ui.theme.TextSecondary
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.omshivgoraksha.data.local.TokenManager
import com.example.omshivgoraksha.data.model.login.viewmodel.LoginUiState
import com.example.omshivgoraksha.data.model.login.viewmodel.LoginViewModel
import com.example.omshivgoraksha.data.model.login.viewmodel.LoginViewModelFactory

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onSignupClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {

    val context = androidx.compose.ui.platform.LocalContext.current
    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.Black
    )
    val tokenManager =
        remember {
            TokenManager(
                context.applicationContext
            )
        }

    val viewModel: LoginViewModel =
        viewModel(
            factory =
                LoginViewModelFactory(
                    tokenManager
                )
        )

    val uiState by
    viewModel.uiState
        .collectAsStateWithLifecycle()

    var username by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var usernameError by remember {
        mutableStateOf<String?>(null)
    }

    var passwordError by remember {
        mutableStateOf<String?>(null)
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    /*
     * After successful login,
     * navigate to Home.
     */

    LaunchedEffect(uiState) {

        if (uiState is LoginUiState.Success) {

            kotlinx.coroutines.delay(500)

            viewModel.resetState()

            onLoginSuccess()
        }
    }

    fun validateForm(): Boolean {

        var valid = true

        usernameError = when {

            username.isBlank() ->
                "Username is required."

            username.trim().length < 4 ->
                "Username must contain at least 4 characters."

            !username.trim().matches(
                Regex("^[a-zA-Z0-9_]+$")
            ) ->
                "Username can contain letters, numbers and underscore only."

            else -> null
        }

        if (usernameError != null) {
            valid = false
        }

        passwordError = when {

            password.isBlank() ->
                "Password is required."

            password.length < 8 ->
                "Password must contain at least 8 characters."

            else -> null
        }

        if (passwordError != null) {
            valid = false
        }

        return valid
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 24.dp,
                vertical = 32.dp
            ),
        horizontalAlignment =
            Alignment.CenterHorizontally,
        verticalArrangement =
            Arrangement.Center
    ) {

        Image(
            painter = painterResource(
                id = R.drawable.osg
            ),
            contentDescription =
                "Om Shiv Goraksha Logo",
            modifier = Modifier.size(130.dp)
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "Welcome Back",
            style =
                MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Sign in to continue",
            style =
                MaterialTheme.typography.bodyMedium,
            color = TextSecondary
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        /*
         * Username
         */

        OutlinedTextField(
            value = username,

            onValueChange = {
                username = it
                usernameError = null
            },

            modifier =
                Modifier.fillMaxWidth(),

            label = {
                Text("Username")
            },

            singleLine = true,

            isError =
                usernameError != null,

            supportingText = {

                usernameError?.let {

                    Text(it)
                }
            },
            colors = textFieldColors
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        /*
         * Password
         */

        OutlinedTextField(
            value = password,

            onValueChange = {
                password = it
                passwordError = null
            },

            modifier =
                Modifier.fillMaxWidth(),

            label = {
                Text("Password")
            },

            singleLine = true,

            isError =
                passwordError != null,

            supportingText = {

                passwordError?.let {

                    Text(it)
                }
            },

            visualTransformation =
                if (passwordVisible) {

                    VisualTransformation.None

                } else {

                    PasswordVisualTransformation()
                },

            trailingIcon = {

                IconButton(
                    onClick = {
                        passwordVisible =
                            !passwordVisible
                    }
                ) {

                    Icon(

                        imageVector =
                            if (passwordVisible) {

                                Icons.Default.VisibilityOff

                            } else {

                                Icons.Default.Visibility
                            },

                        contentDescription =
                            if (passwordVisible) {

                                "Hide password"

                            } else {

                                "Show password"
                            }
                    )
                }
            },
            colors = textFieldColors
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        TextButton(
            onClick =
                onForgotPasswordClick,

            modifier =
                Modifier.align(
                    Alignment.End
                ),

            enabled =
                uiState !is LoginUiState.Loading
        ) {

            Text(
                text = "Forgot Password?",
                color = GoldDark
            )
        }

        /*
         * API message
         */

        when (val state = uiState) {

            is LoginUiState.Error -> {

                Text(
                    text = state.message,
                    color =
                        MaterialTheme.colorScheme.error,
                    style =
                        MaterialTheme.typography.bodyMedium
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }

            is LoginUiState.Success -> {

                Text(
                    text = state.message,
                    color =
                        MaterialTheme.colorScheme.primary,
                    style =
                        MaterialTheme.typography.bodyMedium
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }

            else -> Unit
        }

        /*
         * Login button
         */

        PrimaryButton(

            text =
                if (
                    uiState is LoginUiState.Loading
                ) {
                    "LOGGING IN..."
                } else {
                    "LOGIN"
                },

            onClick = {

                if (
                    uiState is LoginUiState.Loading
                ) {
                    return@PrimaryButton
                }

                if (!validateForm()) {
                    return@PrimaryButton
                }

                viewModel.login(
                    username =
                        username.trim(),

                    password =
                        password
                )
            }
        )

        if (
            uiState is LoginUiState.Loading
        ) {

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            CircularProgressIndicator(
                modifier =
                    Modifier.size(28.dp)
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "Don't have an account?",
            color = TextSecondary
        )

        TextButton(
            onClick = onSignupClick,
            enabled =
                uiState !is LoginUiState.Loading
        ) {

            Text(
                text = "Create Account",
                color = GoldDark,
                fontWeight = FontWeight.Bold
            )
        }
    }
}