package com.example.omshivgoraksha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.omshivgoraksha.data.remote.RetrofitClient
import com.example.omshivgoraksha.navigation.AppNavigation
import com.example.omshivgoraksha.ui.theme.OmShivGorakshaTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)
        RetrofitClient.initialize(
            applicationContext
        )
        setContent {
            OmShivGorakshaTheme {
                AppNavigation()
            }
        }
    }
}