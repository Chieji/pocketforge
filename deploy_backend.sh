#!/bin/bash
#
# PocketForge Backend One-Command Deploy Script
#
# This script assumes you have the Fly.io CLI (flyctl) installed and logged in.
# It also assumes the necessary secrets (FLY_API_TOKEN, DATABASE_URL, GITHUB_CLIENT_ID/SECRET)
# are already configured in your environment or Fly.io app secrets.

set -e

echo "--- PocketForge Backend Deployment Initiated ---"

# 1. Navigate to the backend directory
cd PocketForge/backend

# 2. Check for flyctl
if ! command -v flyctl &> /dev/null
then
    echo "Error: flyctl could not be found. Please install it."
    exit 1
fi

# 3. Deploy the application using the fly.toml configuration
echo "Deploying application 'pocketforge-backend' to Fly.io..."
flyctl deploy --remote-only

echo "--- Deployment Complete ---"
echo "Application URL: https://pocketforge-backend.fly.dev"
echo "To view logs: flyctl logs -a pocketforge-backend"
