package com.example.omshivgoraksha.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.omshivgoraksha.data.local.TokenManager
import com.example.omshivgoraksha.data.model.auth.AuthState
import com.example.omshivgoraksha.data.model.auth.AuthViewModel
import com.example.omshivgoraksha.data.model.auth.AuthViewModelFactory
import com.example.omshivgoraksha.ui.screens.auth.ForgotPasswordScreen
import com.example.omshivgoraksha.ui.screens.auth.LoginScreen
import com.example.omshivgoraksha.ui.screens.auth.SignupScreen
import com.example.omshivgoraksha.ui.screens.course.CourseScreen
import com.example.omshivgoraksha.ui.screens.home.HomeScreen

@Composable
fun AppNavigation() {

    val context = LocalContext.current

    /*
     * TokenManager
     */
    val tokenManager = remember {

        TokenManager(
            context.applicationContext
        )
    }

    /*
     * AuthViewModel
     */
    val authViewModel: AuthViewModel =
        viewModel(
            factory = AuthViewModelFactory(
                tokenManager
            )
        )

    /*
     * Authentication state
     */
    val authState by
    authViewModel.authState
        .collectAsStateWithLifecycle()

    /*
     * NavController
     */
    val navController =
        rememberNavController()

    /*
     * Current route
     */
    val navBackStackEntry by
    navController.currentBackStackEntryAsState()

    val currentRoute =
        navBackStackEntry
            ?.destination
            ?.route
            ?: Screen.Login.route

    /*
     * Wait until JWT validation is completed.
     */
    if (authState is AuthState.Checking) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            CircularProgressIndicator()
        }

        return
    }

    /*
     * Decide where the user should go.
     */
    val startDestination =
        when (authState) {

            AuthState.LoggedIn ->
                Screen.Home.route

            AuthState.LoggedOut ->
                Screen.Login.route

            AuthState.Checking ->
                Screen.Login.route
        }

    /*
     * =========================
     * NAVIGATION
     * =========================
     */

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        // =========================
        // LOGIN
        // =========================

        composable(
            route = Screen.Login.route
        ) {

            LoginScreen(

                onLoginSuccess = {

                    navController.navigate(
                        Screen.Home.route
                    ) {

                        /*
                         * Remove Login from
                         * back stack.
                         */
                        popUpTo(
                            Screen.Login.route
                        ) {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                },

                onSignupClick = {

                    navController.navigate(
                        Screen.Signup.route
                    )
                },

                onForgotPasswordClick = {

                    navController.navigate(
                        Screen.ForgotPassword.route
                    )
                }
            )
        }

        // =========================
        // SIGNUP
        // =========================

        composable(
            route = Screen.Signup.route
        ) {

            SignupScreen(

                onSignupSuccess = {

                    /*
                     * Signup successful.
                     *
                     * User should login with
                     * the newly created account.
                     */
                    navController.navigate(
                        Screen.Login.route
                    ) {

                        popUpTo(
                            Screen.Signup.route
                        ) {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                },

                onLoginClick = {

                    navController.navigate(
                        Screen.Login.route
                    ) {

                        popUpTo(
                            Screen.Signup.route
                        ) {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                }
            )
        }

        // =========================
        // FORGOT PASSWORD
        // =========================

        composable(
            route = Screen.ForgotPassword.route
        ) {

            ForgotPasswordScreen(

                onResetLinkSent = {

                    /*
                     * Implement later.
                     */
                },

                onBackToLogin = {

                    navController.popBackStack()
                }
            )
        }

        // =========================
        // HOME
        // =========================

        composable(
            route = Screen.Home.route
        ) {

            HomeScreen(

                currentRoute =
                    currentRoute,

                onNavigate = { route ->

                    navController.navigate(route) {

                        launchSingleTop = true

                        restoreState = true
                    }
                },

                onLogout = {

                    /*
                     * Clear JWT first.
                     */
                    authViewModel.logout()
                }
            )
        }

        // =========================
        // COURSES
        // =========================

        composable(
            route = Screen.Courses.route
        ) {

            CourseScreen(

                onNavigate = { route ->

                    navController.navigate(route) {

                        launchSingleTop = true

                        restoreState = true
                    }
                },

                onLogout = {

                    /*
                     * Clear JWT.
                     */
                    authViewModel.logout()
                }
            )
        }
    }

    /*
     * =========================
     * LOGOUT / AUTH STATE CHANGE
     * =========================
     *
     * When logout() changes state to
     * LoggedOut, navigate to Login.
     */

    LaunchedEffect(authState) {

        if (authState is AuthState.LoggedOut &&
            currentRoute != Screen.Login.route
        ) {

            navController.navigate(
                Screen.Login.route
            ) {

                popUpTo(0) {
                    inclusive = true
                }

                launchSingleTop = true
            }
        }
    }
}