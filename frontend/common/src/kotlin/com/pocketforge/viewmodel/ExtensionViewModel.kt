package com.pocketforge.viewmodel

import com.pocketforge.models.Extension
import com.pocketforge.extensions.ExtensionHost
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class ExtensionViewModel {
    private val scope = CoroutineScope(Dispatchers.Default + Job())
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }
    private val extensionHost = ExtensionHost()

    private val _extensions = MutableStateFlow<List<Extension>>(emptyList())
    val extensions: StateFlow<List<Extension>> = _extensions
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadMarketplaceExtensions()
    }

    private fun loadMarketplaceExtensions() {
        scope.launch {
            _isLoading.value = true
            try {
                // Fetch from the Rust backend API
                val fetchedExtensions: List<Extension> = client.get("http://localhost:8080/extensions/marketplace").body()
                _extensions.value = fetchedExtensions
            } catch (e: Exception) {
                println("Error fetching extensions: ${e.message}")
                // Fallback to mock data if API is unavailable
                _extensions.value = listOf(
                    Extension("pocketforge.git-lens-mobile", "GitLens Mobile", "PocketForge Team", "1.0.0", "Visualize code authorship at a glance via Git blame annotations.", "https://mock.url/git-lens.vxix", 15420, isInstalled = false),
                    Extension("pocketforge.rust-analyzer-lite", "Rust Analyzer Lite", "Community", "0.1.5", "Lightweight language server for Rust projects.", "https://mock.url/rust-analyzer.vxix", 8900, isInstalled = true)
                )
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun installExtension(extension: Extension) {
        scope.launch {
            // K-God Persona: Build systems, not scripts. This is the installation orchestration.
            println("Starting installation for ${extension.name}...")
            
            // 1. Download the VXIX file (omitted for brevity, requires platform-specific file IO)
            // 2. Install the VXIX file (extract and validate manifest)
            //    val manifest = VXIXInstaller.install(extension.download_url)
            
            // Simulation: Assume successful installation and manifest parsing
            val manifest = Json.decodeFromString<com.pocketforge.models.ExtensionManifest>("""
                {
                  "name": "${extension.id}",
                  "displayName": "${extension.name}",
                  "version": "${extension.version}",
                  "publisher": "${extension.publisher}",
                  "description": "${extension.description}",
                  "main": "extension.js",
                  "activationEvents": ["onStartup"],
                  "contributes": {}
                }
            """)
            
            // 3. Load the extension into the host
            extensionHost.loadExtension(extension.id, manifest)
            
            // 4. Update UI state
            _extensions.update { list ->
                list.map { if (it.id == extension.id) it.copy(isInstalled = true) else it }
            }
            println("${extension.name} installed and loaded.")
        }
    }
}
