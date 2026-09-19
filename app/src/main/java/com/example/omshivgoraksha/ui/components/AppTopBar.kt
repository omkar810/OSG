package com.example.omshivgoraksha.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    onMenuClick: () -> Unit
) {

    TopAppBar(

        title = {
            Text(
                text = "Hello Good Morning!",
                style = MaterialTheme.typography.titleLarge
            )
        },

        navigationIcon = {

            IconButton(
                onClick = onMenuClick
            ) {

                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Open Menu"
                )
            }
        }
    )
}
