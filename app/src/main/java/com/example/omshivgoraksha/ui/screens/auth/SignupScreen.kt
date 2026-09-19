package com.example.omshivgoraksha.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.example.omshivgoraksha.ui.components.PrimaryButton
import com.example.omshivgoraksha.ui.theme.TextSecondary
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import android.util.Patterns
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.omshivgoraksha.data.model.auth.SignupRequest
import com.example.omshivgoraksha.data.model.signup.viewmodel.SignupUiState
import com.example.omshivgoraksha.data.model.signup.viewmodel.SignupViewModel
import com.example.omshivgoraksha.ui.theme.GoldDark

@Composable
fun SignupScreen(
    onSignupSuccess: () -> Unit,
    onLoginClick: () -> Unit,
    viewModel: SignupViewModel = viewModel()
) {

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.Black
    )

    var firstName by remember {
        mutableStateOf("")
    }

    var lastName by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var username by remember {
        mutableStateOf("")
    }

    var mobileNumber by remember {
        mutableStateOf("")
    }

    var whatsappNumber by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    var confirmPasswordVisible by remember {
        mutableStateOf(false)
    }

    /*
     * Validation error messages
     */

    var firstNameError by remember {
        mutableStateOf<String?>(null)
    }

    var lastNameError by remember {
        mutableStateOf<String?>(null)
    }

    var emailError by remember {
        mutableStateOf<String?>(null)
    }

    var usernameError by remember {
        mutableStateOf<String?>(null)
    }

    var mobileError by remember {
        mutableStateOf<String?>(null)
    }

    var whatsappError by remember {
        mutableStateOf<String?>(null)
    }

    var passwordError by remember {
        mutableStateOf<String?>(null)
    }

    var confirmPasswordError by remember {
        mutableStateOf<String?>(null)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    /*
     * Navigate to Login after successful signup.
     */
    LaunchedEffect(uiState) {

        if (uiState is SignupUiState.Success) {

            kotlinx.coroutines.delay(1500)

            viewModel.resetState()

            onSignupSuccess()
        }
    }

    /*
     * Validation function
     */

    fun validateForm(): Boolean {

        var isValid = true

        // First Name
        firstNameError = when {

            firstName.isBlank() ->
                "First name is required."

            firstName.trim().length < 2 ->
                "First name must contain at least 2 characters."

            !firstName.trim()
                .matches(Regex("^[a-zA-Z ]+$")) ->
                "First name can contain only letters."

            else -> null
        }

        if (firstNameError != null) {
            isValid = false
        }

        // Last Name
        lastNameError = when {

            lastName.isBlank() ->
                "Last name is required."

            lastName.trim().length < 2 ->
                "Last name must contain at least 2 characters."

            !lastName.trim()
                .matches(Regex("^[a-zA-Z ]+$")) ->
                "Last name can contain only letters."

            else -> null
        }

        if (lastNameError != null) {
            isValid = false
        }

        // Email
        emailError = when {

            email.isBlank() ->
                "Email address is required."

            !Patterns.EMAIL_ADDRESS
                .matcher(email.trim())
                .matches() ->
                "Enter a valid email address."

            else -> null
        }

        if (emailError != null) {
            isValid = false
        }

        // Username
        usernameError = when {

            username.isBlank() ->
                "Username is required."

            username.trim().length < 4 ->
                "Username must contain at least 4 characters."

            !username.trim()
                .matches(Regex("^[a-zA-Z0-9_]+$")) ->
                "Username can contain letters, numbers and underscore only."

            else -> null
        }

        if (usernameError != null) {
            isValid = false
        }

        // Mobile
        mobileError = when {

            mobileNumber.isBlank() ->
                "Mobile number is required."

            !mobileNumber.matches(
                Regex("^[0-9]{10}$")
            ) ->
                "Enter a valid 10-digit mobile number."

            else -> null
        }

        if (mobileError != null) {
            isValid = false
        }

        // WhatsApp
        whatsappError = when {

            whatsappNumber.isBlank() ->
                "WhatsApp number is required."

            !whatsappNumber.matches(
                Regex("^[0-9]{10}$")
            ) ->
                "Enter a valid 10-digit WhatsApp number."

            else -> null
        }

        if (whatsappError != null) {
            isValid = false
        }

        // Password
        passwordError = when {

            password.isBlank() ->
                "Password is required."

            password.length < 8 ->
                "Password must contain at least 8 characters."

            !password.any { it.isUpperCase() } ->
                "Password must contain at least one uppercase letter."

            !password.any { it.isLowerCase() } ->
                "Password must contain at least one lowercase letter."

            !password.any { it.isDigit() } ->
                "Password must contain at least one number."

            !password.any { !it.isLetterOrDigit() } ->
                "Password must contain at least one special character."

            else -> null
        }

        if (passwordError != null) {
            isValid = false
        }

        // Confirm Password
        confirmPasswordError = when {

            confirmPassword.isBlank() ->
                "Please confirm your password."

            confirmPassword != password ->
                "Passwords do not match."

            else -> null
        }

        if (confirmPasswordError != null) {
            isValid = false
        }

        return isValid
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                horizontal = 24.dp,
                vertical = 32.dp
            ),
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Enter your details to create your account",
            color = TextSecondary,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        /*
         * First Name
         */

        OutlinedTextField(
            value = firstName,
            onValueChange = {
                firstName = it
                firstNameError = null
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("First Name")
            },
            singleLine = true,
            isError = firstNameError != null,
            supportingText = {
                firstNameError?.let {
                    Text(it)
                }
            },
            colors = textFieldColors
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        /*
         * Last Name
         */

        OutlinedTextField(
            value = lastName,
            onValueChange = {
                lastName = it
                lastNameError = null
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Last Name")
            },
            singleLine = true,
            isError = lastNameError != null,
            supportingText = {
                lastNameError?.let {
                    Text(it)
                }
            },
            colors = textFieldColors
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        /*
         * Email
         */

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = null
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Email Address")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true,
            isError = emailError != null,
            supportingText = {
                emailError?.let {
                    Text(it)
                }
            },
            colors = textFieldColors
        )

        Spacer(
            modifier = Modifier.height(14.dp)
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
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Username")
            },
            singleLine = true,
            isError = usernameError != null,
            supportingText = {
                usernameError?.let {
                    Text(it)
                }
            },
            colors = textFieldColors
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        /*
         * Mobile Number
         */

        OutlinedTextField(
            value = mobileNumber,
            onValueChange = {

                if (it.length <= 10 &&
                    it.all { char -> char.isDigit() }
                ) {
                    mobileNumber = it
                    mobileError = null
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Mobile Number")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            ),
            singleLine = true,
            isError = mobileError != null,
            supportingText = {
                mobileError?.let {
                    Text(it)
                }
            },
            colors = textFieldColors
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        /*
         * WhatsApp Number
         */

        OutlinedTextField(
            value = whatsappNumber,
            onValueChange = {

                if (it.length <= 10 &&
                    it.all { char -> char.isDigit() }
                ) {
                    whatsappNumber = it
                    whatsappError = null
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("WhatsApp Number")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            ),
            singleLine = true,
            isError = whatsappError != null,
            supportingText = {
                whatsappError?.let {
                    Text(it)
                }
            },
            colors = textFieldColors
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        /*
         * Password
         */

//        OutlinedTextField(
//            value = password,
//            onValueChange = {
//                password = it
//                passwordError = null
//                confirmPasswordError = null
//            },
//            modifier = Modifier.fillMaxWidth(),
//            label = {
//                Text("Password")
//            },
//            visualTransformation =
//                PasswordVisualTransformation(),
//            singleLine = true,
//            isError = passwordError != null,
//            supportingText = {
//                passwordError?.let {
//                    Text(it)
//                }
//            }
//        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = null
                confirmPasswordError = null
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Password")
            },
            singleLine = true,

            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },

            trailingIcon = {
                IconButton(
                    onClick = {
                        passwordVisible = !passwordVisible
                    }
                ) {
                    Icon(
                        imageVector = if (passwordVisible) {
                            Icons.Default.VisibilityOff
                        } else {
                            Icons.Default.Visibility
                        },
                        contentDescription = if (passwordVisible) {
                            "Hide password"
                        } else {
                            "Show password"
                        }
                    )
                }
            },

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),

            isError = passwordError != null,

            supportingText = {
                passwordError?.let {
                    Text(it)
                }
            },

            colors = textFieldColors
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        /*
         * Confirm Password
         */

//        OutlinedTextField(
//            value = confirmPassword,
//            onValueChange = {
//                confirmPassword = it
//                confirmPasswordError = null
//            },
//            modifier = Modifier.fillMaxWidth(),
//            label = {
//                Text("Confirm Password")
//            },
//            visualTransformation =
//                PasswordVisualTransformation(),
//            singleLine = true,
//            isError = confirmPasswordError != null,
//            supportingText = {
//                confirmPasswordError?.let {
//                    Text(it)
//                }
//            }
//        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                confirmPasswordError = null
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Confirm Password")
            },
            singleLine = true,

            visualTransformation = if (confirmPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },

            trailingIcon = {
                IconButton(
                    onClick = {
                        confirmPasswordVisible = !confirmPasswordVisible
                    }
                ) {
                    Icon(
                        imageVector = if (confirmPasswordVisible) {
                            Icons.Default.VisibilityOff
                        } else {
                            Icons.Default.Visibility
                        },
                        contentDescription = if (confirmPasswordVisible) {
                            "Hide confirm password"
                        } else {
                            "Show confirm password"
                        }
                    )
                }
            },

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),

            isError = confirmPasswordError != null,

            supportingText = {
                confirmPasswordError?.let {
                    Text(it)
                }
            },

            colors = textFieldColors
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        /*
         * API Success / Error message
         */

        when (val state = uiState) {

            is SignupUiState.Success -> {

                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }

            is SignupUiState.Error -> {

                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }

            else -> Unit
        }

        /*
         * Create Account
         */

        PrimaryButton(
            text = if (uiState is SignupUiState.Loading)
                "CREATING ACCOUNT..."
            else
                "CREATE ACCOUNT",

            onClick = {

                if (uiState is SignupUiState.Loading) {
                    return@PrimaryButton
                }

                if (!validateForm()) {
                    return@PrimaryButton
                }

                val request = SignupRequest(

                    userName = username.trim(),

                    firstName = firstName.trim(),

                    lastName = lastName.trim(),

                    email = email.trim(),

                    phoneNumber =
                        mobileNumber.toLong(),

                    wpNumber =
                        whatsappNumber.toLong(),

                    password = password
                )

                viewModel.signupUser(request)
            }
        )

        if (uiState is SignupUiState.Loading) {

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp)
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Already have an account?",
            color = TextSecondary
        )

        TextButton(
            onClick = onLoginClick,
            enabled = uiState !is SignupUiState.Loading
        ) {

            Text(
                text = "Login"
            )
        }
    }
}