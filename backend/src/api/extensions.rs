use axum::{
    response::IntoResponse,
    Json,
};
use http::StatusCode;
use crate::models::Extension;

pub async fn list_marketplace_extensions() -> Json<Vec<Extension>> {
    // K-God Persona: Ship with confidence. Mocking a production-ready marketplace API response.
    Json(vec![
        Extension {
            id: "pocketforge.git-lens-mobile".to_string(),
            name: "GitLens Mobile".to_string(),
            publisher: "PocketForge Team".to_string(),
            version: "1.0.0".to_string(),
            description: "Visualize code authorship at a glance via Git blame annotations.".to_string(),
            download_url: "https://marketplace.pocketforge.app/git-lens-mobile.vxix".to_string(),
            install_count: 15420,
        },
        Extension {
            id: "pocketforge.rust-analyzer-lite".to_string(),
            name: "Rust Analyzer Lite".to_string(),
            publisher: "Community".to_string(),
            version: "0.1.5".to_string(),
            description: "Lightweight language server for Rust projects.".to_string(),
            download_url: "https://marketplace.pocketforge.app/rust-analyzer-lite.vxix".to_string(),
            install_count: 8900,
        },
        Extension {
            id: "pocketforge.docker-compose-ui".to_string(),
            name: "Docker Compose UI".to_string(),
            publisher: "PocketForge Team".to_string(),
            version: "2.1.0".to_string(),
            description: "Graphical interface for managing docker-compose services.".to_string(),
            download_url: "https://marketplace.pocketforge.app/docker-compose-ui.vxix".to_string(),
            install_count: 22000,
        },
    ])
}
