use axum::{
    extract::{Query, State},
    response::{IntoResponse, Redirect},
    Json,
};
use http::StatusCode;
use serde::Deserialize;
use crate::models::{AuthToken, Claims, User};
use jsonwebtoken::{encode, EncodingKey, Header};
use chrono::{Utc, Duration};

// --- Configuration (In a real app, these would be read from environment variables) ---
const GITHUB_CLIENT_ID: &str = "YOUR_GITHUB_CLIENT_ID";
const GITHUB_CLIENT_SECRET: &str = "YOUR_GITHUB_CLIENT_SECRET";
const REDIRECT_URI: &str = "http://localhost:8080/auth/callback";
const JWT_SECRET: &[u8] = b"SUPER_SECRET_KEY_FOR_POCKETFORGE"; // MUST be replaced

// --- Request/Response Models ---

#[derive(Debug, Deserialize)]
pub struct AuthQuery {
    code: String,
    state: Option<String>,
}

// --- Handlers ---

pub async fn github_auth() -> impl IntoResponse {
    // K-God Persona: Write code that ships. Production-ready from the start.
    let url = format!(
        "https://github.com/login/oauth/authorize?client_id={}&redirect_uri={}&scope=repo,user:email",
        GITHUB_CLIENT_ID, REDIRECT_URI
    );
    Redirect::to(&url)
}

pub async fn github_callback(
    Query(query): Query<AuthQuery>,
    // State(pool): State<sqlx::PgPool>, // Assuming we have a DB pool in state
) -> Result<Json<AuthToken>, (StatusCode, String)> {
    // 1. Exchange code for GitHub access token
    let client = reqwest::Client::new();
    let res = client
        .post("https://github.com/login/oauth/access_token")
        .header("Accept", "application/json")
        .json(&serde_json::json!({
            "client_id": GITHUB_CLIENT_ID,
            "client_secret": GITHUB_CLIENT_SECRET,
            "code": query.code,
            "redirect_uri": REDIRECT_URI,
        }))
        .send()
        .await
        .map_err(|e| (StatusCode::INTERNAL_SERVER_ERROR, format!("GitHub token exchange failed: {}", e)))?;

    let github_token_response: serde_json::Value = res.json().await.map_err(|e| (StatusCode::INTERNAL_SERVER_ERROR, format!("Failed to parse GitHub token response: {}", e)))?;
    let github_access_token = github_token_response["access_token"].as_str().ok_or((StatusCode::UNAUTHORIZED, "No access token from GitHub".to_string()))?;

    // 2. Get user info from GitHub
    let user_res = client
        .get("https://api.github.com/user")
        .header("Authorization", format!("Bearer {}", github_access_token))
        .header("User-Agent", "PocketForge-App")
        .send()
        .await
        .map_err(|e| (StatusCode::INTERNAL_SERVER_ERROR, format!("GitHub user info failed: {}", e)))?;

    let github_user: serde_json::Value = user_res.json().await.map_err(|e| (StatusCode::INTERNAL_SERVER_ERROR, format!("Failed to parse GitHub user info: {}", e)))?;
    let github_id = github_user["id"].as_i64().ok_or((StatusCode::INTERNAL_SERVER_ERROR, "Missing GitHub ID".to_string()))?.to_string();
    let github_login = github_user["login"].as_str().ok_or((StatusCode::INTERNAL_SERVER_ERROR, "Missing GitHub login".to_string()))?.to_string();

    // 3. Upsert user into database (Simulated)
    // In a real app, we'd use `supabase::upsert_user(...)` here.
    let user_id = uuid::Uuid::new_v4(); // Mock user ID

    // 4. Generate JWT
    let now = Utc::now();
    let expiration = now + Duration::days(7);
    let claims = Claims {
        user_id,
        exp: expiration.timestamp() as usize,
    };

    let token = encode(&Header::default(), &claims, &EncodingKey::from_secret(JWT_SECRET))
        .map_err(|e| (StatusCode::INTERNAL_SERVER_ERROR, format!("JWT encoding failed: {}", e)))?;

    Ok(Json(AuthToken {
        token,
        expires_at: expiration,
    }))
}
