package com.example.omshivgoraksha.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.omshivgoraksha.ui.components.PrimaryButton
import com.example.omshivgoraksha.ui.theme.TextSecondary

@Composable
fun ForgotPasswordScreen(
    onResetLinkSent: () -> Unit,
    onBackToLogin: () -> Unit
) {

    var email by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Forgot Password?",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Enter your registered email address and we will send you instructions to reset your password.",
            color = TextSecondary,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Email Address")
            },
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        PrimaryButton(
            text = "SEND RESET LINK",
            onClick = {

                // API call will be added later.

                onResetLinkSent()
            }
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        androidx.compose.material3.TextButton(
            onClick = onBackToLogin,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Back to Login"
            )
        }
    }
}