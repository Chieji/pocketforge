# PocketForge Final IDE UI Mockup Description: Integrated Development Environment

This document provides the final, detailed textual description of the complete PocketForge IDE UI, showcasing the integration of the terminal, file explorer, and extension system, designed for a professional, VS Code-like experience on a tablet.

## 1. Overall Layout & Theme

*   **Device:** Tablet (Landscape Orientation).
*   **Theme:** **PocketForge Dark Mode** (Based on VS Code's Monokai/Dark+).
    *   **Background:** `#1E1E1E` (Dark Gray).
    *   **Terminal Text:** `#00FF7F` (Neon Green for prompt), `#D4D4D4` (Off-White for output).
    *   **Accent Color:** `#007ACC` (VS Code Blue).

## 2. Main Screen Components (Three-Panel Layout)

The screen is divided into three primary vertical sections: Activity Bar, Sidebar (File Explorer), and Editor/Terminal Area.

### A. Activity Bar (Leftmost Panel - ~50dp wide)

A thin, fixed vertical bar for primary navigation.

| Icon | Feature | Status | Notes |
| :--- | :--- | :--- | :--- |
| `📄` | **File Explorer** | **Active** | Toggles the File Explorer Sidebar. |
| `🔍` | **Search** | Inactive | Global search across project files. |
| `🧩` | **Extensions** | Inactive | Toggles the Extension Marketplace Sidebar. |
| `🚀` | **Deploy** | Inactive | New icon for One-Tap Deploy (Vercel, Fly.io). |
| `🤖` | **AI Agent** | Inactive | Toggles the AI Agent Chat/Tool-Calling Panel. |
| `⚙️` | **Settings** | Inactive | Application settings. |

### B. Sidebar (File Explorer - ~250dp wide)

This panel is open, displaying the project structure.

*   **Title:** "EXPLORER - /project"
*   **Content:** A tree view of the project structure.
    *   `src/`
        *   `main.rs` (Highlighted as the currently open file)
        *   `api/`
        *   `services/`
    *   `Cargo.toml`
    *   `.gitignore`
    *   `README.md`

### C. Editor/Terminal Area (Main Panel - Remaining Width)

This is the primary workspace, currently showing the integrated terminal.

#### Top Bar (Under the main App Bar)

*   **Tabs:** A tab bar showing open files/terminals.
    *   `main.rs` (Inactive Tab)
    *   `Terminal: pf-c-12345` (**Active Tab**)

#### Terminal View

The terminal occupies the entire main panel, demonstrating the Docker-in-Docker capability.

*   **Prompt:** `user@pf-c-12345:/project$ ` (Neon Green)
*   **Input/Output:**
    ```
    user@pf-c-12345:/project$ docker run -d -p 8080:80 nginx
    Unable to find image 'nginx:latest' locally
    latest: Pulling from library/nginx
    ...
    Status: Downloaded newer image for nginx:latest
    74b8f3d0a1c3...
    user@pf-c-12345:/project$ 
    ```

#### Status Bar (Bottom of the Screen)

A thin bar providing contextual information.

*   **Left:** Container Status: `Running (Fly.io)`
*   **Center:** Git Branch: `main*` (with a small asterisk indicating uncommitted changes)
*   **Right:** Line/Col: `Ln 1, Col 1` (when in editor mode)

This layout provides a professional, feature-rich, and highly usable mobile IDE experience, fulfilling the requirement for a "mini VS Code but powerful."
