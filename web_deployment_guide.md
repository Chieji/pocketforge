# PocketForge Web Deployment Guide: Kotlin/WASM

The Kotlin Multiplatform (KMP) architecture allows the PocketForge frontend to be compiled to WebAssembly (WASM), enabling a full-featured web application accessible via a browser. This guide details the steps for building and deploying the web client.

## 1. Building the Web Client

The KMP project is configured to use the `wasmJs` target, which compiles the Compose Multiplatform UI into JavaScript and WebAssembly.

### 1.1. Build Command

Execute the following command from the root `PocketForge` directory to generate the production-ready web assets:

```bash
./gradlew :frontend:wasmJs:browserProductionWebpack
```

### 1.2. Output Directory

Upon successful execution, the compiled assets will be located in the following directory:

```
PocketForge/frontend/wasmJs/build/distributions/
```

This directory contains:
*   `index.html`: The entry point for the web application.
*   `wasmJs.js`: The JavaScript loader file.
*   `wasmJs.wasm`: The compiled WebAssembly binary containing the application logic.

## 2. Deployment Strategy

The web client is a collection of static files. It can be hosted on any static file hosting service.

### 2.1. Recommended Hosting: Vercel or Netlify

Given the **One-Tap Deploy** feature, Vercel and Netlify are the recommended platforms.

| Platform | Configuration | Notes |
| :--- | :--- | :--- |
| **Vercel** | **Root Directory:** `frontend/wasmJs/build/distributions` | Vercel will automatically detect the static files. |
| **Netlify** | **Base Directory:** `frontend/wasmJs` | **Publish Directory:** `build/distributions` |

### 2.2. Critical Configuration: Backend Proxy

The web client must communicate with the Rust backend API (e.g., `http://localhost:8080` in development, or `https://pocketforge-backend.fly.dev` in production).

**If deploying to a different domain than the backend, a proxy must be configured.**

#### Example: Vercel `vercel.json` Proxy

To allow the web client to call the backend API without CORS issues, a `vercel.json` file should be placed in the root of the deployment directory (`frontend/wasmJs/build/distributions`):

```json
{
  "rewrites": [
    {
      "source": "/api/:path*",
      "destination": "https://pocketforge-backend.fly.dev/:path*"
    }
  ]
}
```

The frontend's Ktor client should then be configured to call `/api/extensions/marketplace` instead of the full backend URL.

## 3. Running Locally

To test the web client locally, you can use a simple static file server (e.g., Python's `http.server`):

```bash
cd PocketForge/frontend/wasmJs/build/distributions
python3 -m http.server 8000
```

The application will be accessible at `http://localhost:8000`.

This deployment strategy ensures the web application is fast, scalable, and leverages the KMP architecture for maximum code reuse.
