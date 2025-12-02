package com.pocketforge

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.pocketforge.ui.screens.LoginScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(LoginScreen)
    }
}

expect fun getPlatformName(): String
