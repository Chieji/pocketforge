use axum::{
    response::IntoResponse,
    Json,
};
use http::StatusCode;
use serde::Deserialize;
use crate::services::mcp;

#[derive(Debug, Deserialize)]
pub struct DownloadModelRequest {
    pub model_id: String,
    pub container_id: String,
}

pub async fn download_huggingface_model(Json(payload): Json<DownloadModelRequest>) -> impl IntoResponse {
    match mcp::download_hf_model(&payload.model_id, &payload.container_id) {
        Ok(message) => (StatusCode::OK, Json(serde_json::json!({"status": "success", "message": message}))),
        Err(e) => (StatusCode::INTERNAL_SERVER_ERROR, Json(serde_json::json!({"status": "error", "message": e}))),
    }
}
