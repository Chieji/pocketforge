# PocketForge Architectural Blueprint v2.0: Full MCP Integration

The PocketForge architecture is upgraded to integrate all available Model Context Protocol (MCP) servers, transforming the application into a robust, all-in-one development and operations platform. This adheres to the K-God directive to **Build systems, not scripts**.

## 1. MCP Integration Layer (Rust Backend)

The Rust/Axum backend acts as a secure proxy and orchestration layer for all MCP interactions. This prevents exposing sensitive API keys to the mobile frontend.

### 1.1. MCP Service Contract

A new service module, `services::mcp`, will handle all communication with the MCP servers via the `manus-mcp-cli` utility (simulated via Rust's `std::process::Command`).

| MCP Server | PocketForge Feature | Rust Service Module | API Endpoint Example |
| :--- | :--- | :--- | :--- |
| **Hugging Face** | AI Model Downloader | `services::mcp::huggingface` | `POST /api/v1/ai/download-model` |
| **Vercel** | One-Tap Deploy | `services::mcp::vercel` | `POST /api/v1/deploy/vercel` |
| **Notion** | Documentation/Note Sync | `services::mcp::notion` | `POST /api/v1/sync/notion` |
| **Asana/Linear** | Task Management | `services::mcp::task_manager` | `GET /api/v1/tasks/list` |
| **Prisma-Postgres** | Database Management | `services::mcp::prisma` | `POST /api/v1/db/query` |

### 1.2. Hugging Face Model Downloader

This is a critical new feature, enabling the download of models directly into the Firecracker microVM for local execution (e.g., via Ollama).

*   **Frontend Action:** User selects a model in the "AI Models" tab.
*   **Backend Endpoint:** `POST /api/v1/ai/download-model`
    *   **Input:** `model_id` (e.g., "mistralai/Mistral-7B-v0.1"), `container_id`.
    *   **Logic:** The Rust service calls the Hugging Face MCP to retrieve the model file URL, then orchestrates the download *directly* to the target `container_id`'s storage volume.

## 2. Extension System Enhancement: VSIX to VXIX Translation

To support the user's request for installing standard VS Code extensions (VSIX files), a translation layer is required.

### 2.1. VSIX vs. VXIX

| Feature | VSIX (VS Code) | VXIX (PocketForge) |
| :--- | :--- | :--- |
| **Runtime** | Node.js / Electron | Sandboxed JavaScript (via KMP JS engine) |
| **API** | VS Code Extension API | PocketForge Extension API (subset) |
| **Package** | ZIP (renamed to VSIX) | ZIP (renamed to VXIX) |

### 2.2. Translation Service

The PocketForge marketplace backend will host a **VSIX-to-VXIX Translation Service**.

1.  **User Action:** User attempts to install a VSIX extension.
2.  **Backend Action:** The Rust backend intercepts the request and calls the translation service.
3.  **Translation Logic (Simulated):**
    *   Download the VSIX file.
    *   Extract the contents.
    *   **Transpile:** Convert the VSIX's Node.js-dependent JavaScript (`extension.js`) to a compatible, sandboxed JavaScript version. This is the most complex step, requiring polyfills for Node.js APIs.
    *   **Re-package:** Create a new VXIX file with the transpiled code and the original assets.
4.  **Delivery:** The new VXIX file URL is returned to the mobile frontend for installation.

This approach allows PocketForge to leverage the vast VS Code ecosystem while maintaining a secure, mobile-optimized runtime.

## 3. UI Integration Points

The frontend will be updated to include dedicated UI elements for these new features:

*   **Activity Bar:** New icons for "Deploy" (Vercel) and "Tasks" (Asana/Linear).
*   **Terminal Toolbar:** A button to open the "AI Model Downloader" view.
*   **Context Menus:** Options to "Sync to Notion" or "Create Asana Task from Error Log."

This comprehensive integration ensures PocketForge is not just a mini-IDE, but a powerful, connected development hub.
