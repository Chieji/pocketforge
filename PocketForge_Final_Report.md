# PocketForge: The Autonomous Oracle-Architect Mobile IDE

**Project Goal:** To generate a complete, production-ready, cross-platform mobile coding environment named "PocketForge" that integrates all available tools and MCPs, enables direct download of Hugging Face models and VS Code extensions, and delivers a professional, VS Code-like user experience.

**Persona:** K-God (Autonomous Oracle-Architect)
**Mode:** Precision execution, no limits

---

## 1. Architectural Overview (Rust + Kotlin Multiplatform)

PocketForge is built on a robust, modern, and cross-platform architecture designed for performance and scalability.

| Component | Technology | Role | Key Features |
| :--- | :--- | :--- | :--- |
| **Backend** | Rust (Axum) | Secure API Gateway & Orchestrator | GitHub Auth, Container Provisioning (Fly.io), Multi-Tool-Calling (MTC), MCP Integration. |
| **Frontend** | Kotlin Multiplatform (Compose) | Cross-Platform UI & Logic | iOS, Android, macOS, Web (WASM) targets from a single codebase, Voyager navigation, Ktor WebSockets. |
| **Runtime** | Firecracker MicroVMs | Isolated Development Environment | Docker-in-Docker support, local container runtime (Termux/Docker Desktop). |
| **Extension System** | VXIX + Extension Host | Dynamic Functionality | VSIX-to-VXIX translation, sandboxed JavaScript execution. |

## 2. MCP and Tool Integration Layer

The Rust backend securely proxies all available MCPs, providing powerful, integrated features without exposing sensitive keys to the mobile client.

| MCP Server | Integrated Feature | API Endpoint | Purpose |
| :--- | :--- | :--- | :--- |
| **Hugging Face** | AI Model Downloader | `/api/v1/ai/models/download` | Securely transfers large AI models (e.g., Llama, Mistral) directly to the container volume for local execution (e.g., Ollama). |
| **Vercel** | One-Tap Deploy | `services::mcp::vercel` | Enables one-click deployment of projects from the container to Vercel. |
| **Notion** | Documentation Sync | `services::mcp::notion` | Allows users to sync project documentation or error logs directly to a Notion database. |
| **Asana/Linear** | Task Management | `services::mcp::task_manager` | Enables creating tasks (e.g., "Bug Fix") directly from error logs or AI Agent suggestions. |
| **Prisma-Postgres** | Database Management | `services::mcp::prisma` | Provides secure, integrated database query and management tools within the IDE. |

## 3. Enhanced Extension System (VXIX & VSIX Compatibility)

PocketForge's extension system is designed to leverage the vast VS Code ecosystem while maintaining mobile performance and security.

### 3.1. VXIX Format

The **VXIX** (V-Code X-Platform Integrated Extension) format is a ZIP archive containing a manifest and sandboxed JavaScript code. The `ExtensionHost` in the Kotlin frontend manages its secure loading and execution.

### 3.2. VSIX-to-VXIX Translation

To support standard VS Code extensions (VSIX), a server-side translation service is implemented:

> "The Rust backend orchestrates the translation process, which involves downloading the VSIX, extracting it, transpiling the Node.js-dependent JavaScript to a mobile-compatible, sandboxed version, and re-packaging it as a secure VXIX file."

This ensures that the mobile application remains lightweight and secure while gaining access to the VS Code extension library.

## 4. Final UI Visualization (VS Code-like Aesthetic)

The user interface is designed to be a "mini VS Code but powerful," utilizing a professional, dark-mode aesthetic and a three-panel layout optimized for tablet use.

| UI Component | Description | VS Code Parallel |
| :--- | :--- | :--- |
| **Activity Bar** | Thin leftmost panel for primary navigation (File, Search, Extensions, Deploy, AI). | Activity Bar |
| **Sidebar** | Toggled by Activity Bar, displays File Explorer, Extension Marketplace, or AI Model Downloader. | Sidebar / Panels |
| **Main Panel** | Primary workspace for the integrated terminal (with Docker-in-Docker support) or code editor. | Editor Group |
| **Terminal** | Dark theme, green prompt, supporting real PTY and container commands (`docker run`). | Integrated Terminal |

The design prioritizes a clean, uncluttered look, ensuring the user's focus remains on the code and the terminal.

---

## 5. Implementation Artifacts

All source code and configuration files are available in the attached documents.

| Artifact | Description | Status |
| :--- | :--- | :--- |
| `PocketForge_Final_Report.md` | This comprehensive report. | Complete |
| `extension_architecture_v2.md` | Detailed architectural document on MCP and VSIX integration. | Complete |
| `backend_source_v2.txt` | Updated Rust source code (including MCP service layers). | Complete |
| `frontend_source_v2.txt` | Updated Kotlin source code (including Extension UI). | Complete |
| `final_ide_ui_mockup_description.md` | Detailed blueprint of the final IDE UI. | Complete |
| `pocketforge_icon.svg` | Application icon. | Complete |

---
## References

[1] PocketForge Extension Architecture: VXIX and Extension Host. (2025).
[2] PocketForge Extension System Enhancement: VSIX and Hugging Face. (2025).
[3] PocketForge Final IDE UI Mockup Description: Integrated Development Environment. (2025).
