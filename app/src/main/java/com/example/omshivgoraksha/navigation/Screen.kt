package com.example.omshivgoraksha.navigation

sealed class Screen(
    val route: String
) {
        data object Login : Screen("login")

        data object Courses : Screen("courses")
        data object Signup : Screen("signup")

        data object ForgotPassword : Screen("forgot_password")

        data object Home : Screen("home")

        data object Lessons :
                Screen("lessons/{courseId}") {

                fun createRoute(
                        courseId: Long
                ): String {

                        return "lessons/$courseId"
                }
        }
}