# PocketForge Extension Architecture: VXIX and Extension Host

The core requirement is to introduce a VS Code-like extension system, enabling users to install functionality via a custom package format, **VXIX** (V-Code X-Platform Integrated Extension).

## 1. VXIX File Format Specification

A **VXIX** file is a standard ZIP archive with a specific internal structure, designed for cross-platform compatibility and secure loading.

| Component | Purpose | Required |
| :--- | :--- | :--- |
| `extension.json` | **Manifest File.** Defines metadata, activation events, and entry points. | Yes |
| `extension.js` | **Main Entry Point.** JavaScript code executed by the Extension Host. | Yes |
| `assets/` | Directory for icons, images, and other static resources. | No |
| `bin/` | Directory for platform-specific binaries (e.g., Termux/proot scripts). | No |

### `extension.json` Manifest Structure

The manifest is the contract between the extension and the PocketForge Extension Host.

```json
{
  "name": "git-lens-mobile",
  "displayName": "GitLens Mobile",
  "version": "1.0.0",
  "publisher": "pocketforge",
  "description": "Visualize code authorship at a glance via Git blame annotations.",
  "main": "extension.js",
  "activationEvents": [
    "onCommand:gitlens.toggleBlame",
    "onStartup"
  ],
  "contributes": {
    "commands": [
      {
        "command": "gitlens.toggleBlame",
        "title": "GitLens: Toggle Blame"
      }
    ],
    "views": {
      "activitybar": [
        {
          "id": "gitlens.view",
          "name": "GitLens"
        }
      ]
    }
  }
}
```

## 2. Backend Extension Marketplace API (Rust/Axum)

The backend will host the marketplace API, serving metadata about available extensions.

*   **Endpoint:** `GET /api/v1/extensions/marketplace`
*   **Function:** Returns a list of `Extension` metadata objects.
*   **Model:** The `Extension` model is defined in `backend/src/models/mod.rs`.

## 3. Frontend Extension Host (Kotlin Multiplatform)

The `ExtensionHost` is a core service responsible for:

1.  **Installation:** Parsing the `VXIX` ZIP file, validating the manifest, and extracting contents to a secure local storage path (`/data/pocketforge/extensions/{id}`).
2.  **Lifecycle Management:** Loading `extension.js` into a sandboxed JavaScript runtime (e.g., Kotlin/JS engine on desktop/web, or a WebView-based runtime on mobile).
3.  **API Bridge:** Providing a secure bridge between the sandboxed JavaScript extension code and the native Kotlin/Compose UI/Terminal APIs.
4.  **Contribution:** Reading the `contributes` section of the manifest and dynamically injecting UI elements (e.g., new buttons in the terminal toolbar, new tabs in the sidebar).

### Key Frontend Components

| Component | Location | Role |
| :--- | :--- | :--- |
| `ExtensionHost` | `frontend/common/src/kotlin/com/pocketforge/extensions/ExtensionHost.kt` | Manages extension loading, sandboxing, and communication. |
| `ExtensionManagerScreen` | `frontend/common/src/kotlin/com/pocketforge/ui/screens/ExtensionManagerScreen.kt` | The UI for browsing and installing extensions. |
| `VXIXInstaller` | `frontend/common/src/kotlin/com/pocketforge/extensions/VXIXInstaller.kt` | Handles the ZIP extraction and file system operations for installation. |
