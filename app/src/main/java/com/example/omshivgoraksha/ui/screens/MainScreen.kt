package com.example.omshivgoraksha.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.omshivgoraksha.ui.components.AppSidebar
import com.example.omshivgoraksha.ui.components.AppTopBar
import com.example.omshivgoraksha.ui.components.BottomNavigationBar
import com.example.omshivgoraksha.ui.components.UserRole
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    userRole: UserRole,
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {

    // ---------------------------------------------------------
    // Drawer State
    // ---------------------------------------------------------

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()


    // ---------------------------------------------------------
    // Navigation Drawer
    // ---------------------------------------------------------

    ModalNavigationDrawer(

        drawerState = drawerState,

        drawerContent = {

            AppSidebar(
                userRole = userRole,
                currentRoute = currentRoute,

                onItemClick = { route ->

                    // Close drawer
                    scope.launch {
                        drawerState.close()
                    }

                    // Navigate to selected screen
                    onNavigate(route)
                },

                onLogoutClick = {

                    // Close drawer
                    scope.launch {
                        drawerState.close()
                    }

                    // Logout
                    onLogout()
                },

                modifier = Modifier
            )
        }

    ) {

        // -----------------------------------------------------
        // Main Application Scaffold
        // -----------------------------------------------------

        Scaffold(

            topBar = {

                AppTopBar(
                    onMenuClick = {

                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            },

            bottomBar = {

                BottomNavigationBar(
                    currentRoute = currentRoute,
                    onItemClick = onNavigate
                )
            }

        ) { paddingValues ->

            content(
                paddingValues
            )
        }
    }
}

