package com.pocketforge.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

object LoginScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                TopAppBar(title = { Text("PocketForge") })
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Welcome to PocketForge",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                
                Button(
                    onClick = {
                        // In a real app, this would initiate the GitHub OAuth flow
                        println("Initiating GitHub Login...")
                        // Mock navigation to terminal with a placeholder container ID
                        navigator.push(TerminalScreen("pf-c-12345"))
                    },
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("Sign in with GitHub")
                }
                
                Spacer(Modifier.height(16.dp))
                
                TextButton(onClick = {
                    // Placeholder for offline mode entry
                    navigator.push(TerminalScreen("local-c-0001"))
                }) {
                    Text("Continue in Offline Mode")
                }
            }
        }
    }
}
