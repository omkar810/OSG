package com.example.omshivgoraksha.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

data class BottomNavItem(
    val title: String,
    val route: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
fun BottomNavigationBar(
    currentRoute: String,
    onItemClick: (String) -> Unit
) {

    val items = listOf(
        BottomNavItem(
            title = "Home",
            route = "home",
            icon = Icons.Default.Home
        ),
        BottomNavItem(
            title = "Courses",
            route = "courses",
            icon = Icons.Default.MenuBook
        ),
        BottomNavItem(
            title = "Profile",
            route = "profile",
            icon = Icons.Default.Person
        )
    )

    NavigationBar {

        items.forEach { item ->

            NavigationBarItem(

                selected = currentRoute == item.route,

                onClick = {
                    onItemClick(item.route)
                },

                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },

                label = {
                    Text(
                        text = item.title
                    )
                }
            )
        }
    }
}