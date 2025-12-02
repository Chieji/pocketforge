package com.pocketforge.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.pocketforge.models.Extension
import com.pocketforge.viewmodel.ExtensionViewModel

object ExtensionManagerScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = remember { ExtensionViewModel() }
        val extensions by viewModel.extensions.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Extensions: Marketplace") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                if (isLoading) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
                
                LazyColumn(contentPadding = PaddingValues(8.dp)) {
                    items(extensions) { extension ->
                        ExtensionCard(extension, viewModel::installExtension)
                        Divider()
                    }
                }
            }
        }
    }
}

@Composable
fun ExtensionCard(extension: Extension, onInstall: (Extension) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Navigate to detail view */ }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(extension.name, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(
                text = "${extension.publisher} • v${extension.version} • ${extension.install_count} installs",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(8.dp))
            Text(extension.description, style = MaterialTheme.typography.bodyMedium)
        }
        
        Spacer(Modifier.width(16.dp))
        
        Button(
            onClick = { onInstall(extension) },
            enabled = !extension.isInstalled,
            modifier = Modifier.wrapContentWidth()
        ) {
            if (extension.isInstalled) {
                Text("Installed")
            } else {
                Icon(Icons.Filled.Download, contentDescription = "Install", modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(4.dp))
                Text("Install")
            }
        }
    }
}
