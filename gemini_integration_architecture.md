# PocketForge Gemini AI Integration Architecture

The user has requested the intelligent utilization of the connected Gemini API. This integration will enhance the core **Autonomous AI Agent** functionality by providing a powerful, multimodal LLM backend for complex tasks.

## 1. Integration Strategy: Intelligent Routing

The existing Multi-Tool-Calling (MTC) endpoint (`/api/v1/ai/mtc`) will be upgraded to intelligently route requests to the most capable LLM backend (Claude, Grok, or Gemini) based on the user's prompt and the required toolset.

| LLM Backend | Primary Use Case | Key Advantage |
| :--- | :--- | :--- |
| **Gemini** | **Complex Reasoning, Multimodal, Code Generation** | Superior performance on code-related tasks, complex logic, and image analysis (e.g., debugging a screenshot of an error). |
| **Claude/Grok** | **General MTC, Conversational** | Fast, reliable MTC execution and high-quality natural language responses. |

## 2. Rust Service Layer: `services::gemini`

A new service module will be created in the Rust backend to handle the communication with the Gemini API.

### 2.1. Dependencies

The Rust backend's `Cargo.toml` will require a suitable HTTP client and JSON parsing libraries (already present: `reqwest`, `serde_json`).

### 2.2. API Contract

The `services::gemini` module will expose a function that mirrors the MTC service, allowing the main MTC handler to call it seamlessly.

```rust
// services/gemini.rs (Simulated)

pub async fn call_gemini_mtc(prompt: &str, context: &str, tools: Vec<String>) -> Result<MTCResponse, String> {
    // 1. Construct the Gemini API request payload (including tools and system instructions).
    // 2. Use the GEMINI_API_KEY environment variable for authentication.
    // 3. Send the request to the Gemini API endpoint.
    // 4. Parse the response, specifically looking for function/tool calls.
    // 5. Map the Gemini tool calls to the PocketForge MTCResponse format.
    
    // ... actual implementation ...
    
    // Example: If the prompt is "Analyze this image and fix the bug in main.rs"
    if prompt.contains("image") {
        // Use a Gemini model with multimodal capabilities (e.g., gemini-2.5-flash)
        // The MTC agent would first call a tool to upload the image to a temporary URL.
        // The URL is then passed to the Gemini API.
    }
    
    // Return the structured response
    Ok(MTCResponse { /* ... */ })
}
```

## 3. Gemini-Enhanced AI Agent Workflow

The integration of Gemini elevates the AI Agent's capabilities, particularly in the following scenarios:

| Scenario | Gemini Role |
| :--- | :--- |
| **Debugging from Screenshot** | The user uploads a screenshot of a terminal error. Gemini analyzes the image, identifies the error type, and generates a multi-step tool-call sequence to read the relevant file, run a diagnostic command, and apply a fix. |
| **Complex Code Refactoring** | The user requests a refactoring of a large Rust module. Gemini uses its superior code reasoning to generate a series of precise `file_write` tool calls, ensuring architectural integrity. |
| **New Feature Scaffolding** | The user asks to "Scaffold a new Axum endpoint for user profile." Gemini generates the required Rust code, including models, handlers, and tests, ready for the MTC agent to write to the file system. |

By intelligently routing complex, code-heavy, or multimodal requests to the Gemini API, PocketForge maintains its promise of being an **Autonomous Oracle-Architect** in the user's pocket.
