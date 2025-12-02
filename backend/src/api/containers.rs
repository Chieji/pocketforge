use axum::{
    extract::{Path, WebSocketUpgrade, ws::{WebSocket, Message}},
    response::IntoResponse,
    Json,
};
use http::StatusCode;
use serde::{Serialize, Deserialize};
use uuid::Uuid;
use crate::models::Container;
use crate::services::flyio;

// --- Request/Response Models ---

#[derive(Debug, Deserialize)]
pub struct CreateContainerRequest {
    image: String,
    repo_url: Option<String>,
    // Add fields for BYO-Cloud keys if necessary
}

// --- Handlers ---

pub async fn list_containers() -> Json<Vec<Container>> {
    // In a real app, this would query the Supabase database for the user's containers
    // Mock data for container list
    Json(vec![
        Container {
            id: Uuid::new_v4(),
            user_id: Uuid::new_v4(),
            fly_app_name: "pf-user-123-c1".to_string(),
            image: "alpine:latest".to_string(),
            status: "running".to_string(),
            external_port: 8080,
            created_at: chrono::Utc::now(),
            last_used_at: chrono::Utc::now(),
        },
    ])
}

pub async fn create_container(Json(payload): Json<CreateContainerRequest>) -> impl IntoResponse {
    // K-God Persona: Build systems, not scripts. This function orchestrates the VM creation.
    let app_name = format!("pf-user-{}-{}", Uuid::new_v4().to_string().split('-').next().unwrap_or("temp"), Uuid::new_v4().to_string().split('-').next().unwrap_or("temp"));

    match flyio::provision_firecracker_vm(&app_name).await {
        Ok(msg) => {
            // In a real app, we would save the new container record to Supabase here.
            tracing::info!("Container provisioned: {}", msg);
            (StatusCode::CREATED, Json(serde_json::json!({"message": "Container creation initiated.", "app_name": app_name})))
        }
        Err(e) => {
            tracing::error!("Failed to provision container: {:?}", e);
            (StatusCode::INTERNAL_SERVER_ERROR, Json(serde_json::json!({"error": "Failed to provision container on Fly.io"})))
        }
    }
}

pub async fn terminal_websocket(
    ws: WebSocketUpgrade,
    Path(id): Path<String>,
) -> impl IntoResponse {
    ws.on_upgrade(move |socket| handle_socket(socket, id))
}

async fn handle_socket(mut socket: WebSocket, id: String) {
    // This is the core of the terminal. It needs to proxy the WebSocket connection
    // to the actual PTY server running inside the Firecracker microVM.
    // The PTY server is assumed to be running a libssh-based WebSocket proxy.

    tracing::info!("Terminal connection established for container: {}", id);
    
    // Placeholder for PTY connection logic
    while let Some(msg) = socket.recv().await {
        if let Ok(msg) = msg {
            match msg {
                Message::Text(t) => {
                    // Forward text to remote PTY server
                    tracing::debug!("Received terminal input: {}", t);
                    // Mock response: Echo the command and a prompt
                    let output = format!("$ {}\n", t);
                    if socket.send(Message::Text(output)).await.is_err() {
                        break;
                    }
                }
                Message::Close(_) => {
                    tracing::info!("Terminal connection closed for container: {}", id);
                    break;
                }
                _ => {}
            }
        } else {
            // Client disconnected
            break;
        }
    }
}
