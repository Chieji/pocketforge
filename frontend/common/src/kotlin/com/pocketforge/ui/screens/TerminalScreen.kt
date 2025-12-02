package com.pocketforge.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.pocketforge.ui.components.TerminalView
import com.pocketforge.viewmodel.TerminalViewModel

data class TerminalScreen(val containerId: String) : Screen {
    @Composable
    override fun Content() {
        val viewModel = remember { TerminalViewModel(containerId) }
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("PocketForge - Container: $containerId") },
                    actions = {
                        IconButton(onClick = { navigator.push(ExtensionManagerScreen) }) {
                            Icon(Icons.Filled.Extension, contentDescription = "Extensions")
                        }
                    }
                )
            }
        ) { paddingValues ->
            TerminalView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                viewModel = viewModel
            )
        }
    }
}
