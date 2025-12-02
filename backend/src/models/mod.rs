use serde::{Serialize, Deserialize};
use uuid::Uuid;
use chrono::{DateTime, Utc};

#[derive(Debug, Serialize, Deserialize, sqlx::FromRow)]
pub struct User {
    pub id: Uuid,
    pub github_id: String,
    pub github_login: String,
    pub email: Option<String>,
    pub is_pro: bool,
    pub created_at: DateTime<Utc>,
    pub updated_at: DateTime<Utc>,
}

#[derive(Debug, Serialize, Deserialize, sqlx::FromRow)]
pub struct Container {
    pub id: Uuid,
    pub user_id: Uuid,
    pub fly_app_name: String,
    pub image: String,
    pub status: String, // e.g., "pending", "running", "stopped", "sleeping"
    pub external_port: u16,
    pub created_at: DateTime<Utc>,
    pub last_used_at: DateTime<Utc>,
}

#[derive(Debug, Serialize, Deserialize)]
pub struct AuthToken {
    pub token: String,
    pub expires_at: DateTime<Utc>,
}

#[derive(Debug, Serialize, Deserialize)]
pub struct Claims {
    pub user_id: Uuid,
    pub exp: usize,
}

#[derive(Debug, Serialize, Deserialize)]
pub struct Extension {
    pub id: String, // e.g., "pocketforge.git-lens-mobile"
    pub name: String,
    pub publisher: String,
    pub version: String,
    pub description: String,
    pub download_url: String, // URL to the VXIX file
    pub install_count: i32,
}
