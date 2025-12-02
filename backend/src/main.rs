use axum::{
    routing::{get, post},
    Router,
};
use std::net::SocketAddr;
use tracing_subscriber::{layer::SubscriberExt, util::SubscriberInitExt};

mod api;
mod models;
mod services;

#[tokio::main]
async fn main() {
    // Initialize tracing (logging)
    tracing_subscriber::registry()
        .with(tracing_subscriber::EnvFilter::new(
            std::env::var("RUST_LOG").unwrap_or_else(|_| "pocketforge_backend=debug,tower_http=debug".into()),
        ))
        .with(tracing_subscriber::fmt::layer())
        .init();

    // Database connection pool (using a placeholder for now)
    // let pool = sqlx::PgPool::connect(&std::env::var("DATABASE_URL").expect("DATABASE_URL must be set"))
    //     .await
    //     .expect("Failed to connect to Postgres.");

    // Build the application router
    let app = Router::new()
        // Public routes
        .route("/health", get(api::health::health_check))
        .route("/auth/github", get(api::auth::github_auth))
        .route("/auth/callback", get(api::auth::github_callback))
        // Authenticated routes
        .route("/containers", get(api::containers::list_containers).post(api::containers::create_container))
        .route("/containers/:id/terminal", get(api::containers::terminal_websocket))
        .route("/ai/mtc", post(api::ai::mtc_handler))
        .route("/ai/models/download", post(api::ai_models::download_huggingface_model)) // NEW: Hugging Face Model Download
        .route("/extensions/marketplace", get(api::extensions::list_marketplace_extensions))
        // .layer(Extension(pool)) // Add database pool to state
        .layer(
            // Middleware for logging and CORS
            tower_http::trace::TraceLayer::new_for_http(),
        );

    // Run the server
    let addr = SocketAddr::from(([0, 0, 0, 0], 8080));
    tracing::debug!("listening on {}", addr);
    axum::Server::bind(&addr)
        .serve(app.into_make_service())
        .await
        .unwrap();
}
