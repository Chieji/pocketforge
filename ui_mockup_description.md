# PocketForge UI Mockup Description: Extension Marketplace

This document provides a detailed textual description of the user interface for the PocketForge Extension Marketplace, serving as a blueprint for implementation. This is a fallback as the direct image generation failed.

## 1. Overall Layout & Theme

*   **Device:** Tablet (e.g., iPad Pro, 12.9-inch).
*   **Orientation:** Landscape.
*   **Theme:** Dark Mode (VS Code aesthetic).
    *   **Background:** `#1E1E1E` (Dark Gray).
    *   **Primary Text:** `#D4D4D4` (Off-White).
    *   **Accent Color:** `#007ACC` (VS Code Blue).

## 2. Main Screen Components

The screen is divided into a persistent Activity Bar on the left and a main content area on the right.

### A. Activity Bar (Left Side)

A thin vertical bar on the far left, approximately 50dp wide.

| Icon | Description | Status |
| :--- | :--- | :--- |
| `📄` (Files) | Toggles the File Explorer view. | Active |
| `🔍` (Search) | Toggles the global search view. | Inactive |
| `⚙️` (Terminal) | Toggles the main Terminal view. | Inactive |
| `🧩` (Extensions) | **Toggles the Extension Marketplace view.** | **Active** |
| `👤` (Account) | User profile and settings. | Inactive |

### B. Main Content Area (Right Side)

This area displays the **Extension Marketplace**.

#### Top Bar

*   **Title:** "Extensions: Marketplace"
*   **Search Bar:** A prominent search input field with the placeholder text "Search Extensions in Marketplace".

#### Extension List

A vertically scrolling list of extension cards.

**Example Extension Card 1: "GitLens Mobile"**

*   **Layout:** A horizontal card with an icon, text details, and an install button.
*   **Icon:** A stylized Git branch icon.
*   **Details:**
    *   **Title:** "GitLens Mobile" (Bold, White)
    *   **Publisher & Stats:** "PocketForge Team • v1.0.0 • 15.4k installs" (Gray)
    *   **Description:** "Visualize code authorship at a glance via Git blame annotations."
*   **Button:**
    *   **Text:** "Install"
    *   **Color:** Green (`#00FF7F`)
    *   **Icon:** A download cloud icon.

**Example Extension Card 2: "Rust Analyzer Lite"**

*   **Layout:** Same as above.
*   **Icon:** The Rust Ferris the Crab logo.
*   **Details:**
    *   **Title:** "Rust Analyzer Lite" (Bold, White)
    *   **Publisher & Stats:** "Community • v0.1.5 • 8.9k installs" (Gray)
    *   **Description:** "Lightweight language server for Rust projects."
*   **Button:**
    *   **Text:** "Installed"
    *   **Color:** Dark Gray (`#333333`)
    *   **State:** Disabled.

This detailed description provides a clear visual and functional guide for the development of the PocketForge Extension Marketplace UI, consistent with the user's request for a VS Code-like experience.
