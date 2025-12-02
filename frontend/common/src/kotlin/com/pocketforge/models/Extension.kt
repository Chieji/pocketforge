package com.pocketforge.models

import kotlinx.serialization.Serializable

@Serializable
data class Extension(
    val id: String, // e.g., "pocketforge.git-lens-mobile"
    val name: String,
    val publisher: String,
    val version: String,
    val description: String,
    val download_url: String, // URL to the VXIX file
    val install_count: Int,
    val isInstalled: Boolean = false
)

@Serializable
data class ExtensionManifest(
    val name: String,
    val displayName: String,
    val version: String,
    val publisher: String,
    val description: String,
    val main: String, // e.g., "extension.js"
    val activationEvents: List<String>,
    val contributes: Map<String, kotlinx.serialization.json.JsonElement>
)
