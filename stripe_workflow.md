# Stripe Price IDs and Subscription Workflow

## 1. Stripe Price IDs (Simulated)

The following are the required Stripe Price IDs for the PocketForge Pro Tier. These IDs are generated in the Stripe Dashboard and are used to identify the subscription product in the API calls.

| Product | Tier | Price | Interval | Price ID (Live Mode) |
| :--- | :--- | :--- | :--- | :--- |
| PocketForge Pro | Monthly | $9.00 | Month | `price_1PqY1f2eZvKYlo2Cg9Xy0Z1A` |
| PocketForge Pro | Yearly | $79.00 | Year | `price_1PqY1f2eZvKYlo2Cg9Xy0Z1B` |

## 2. Subscription Workflow

The subscription process is integrated into the Rust backend and the Kotlin Multiplatform frontend.

### A. Frontend (Kotlin Multiplatform)

1.  **User Action:** The user navigates to the "Upgrade to Pro" screen and selects either the Monthly or Yearly plan.
2.  **API Call:** The frontend calls the Rust backend endpoint: `POST /api/v1/stripe/create-checkout-session` with the selected `price_id` and the user's JWT.
    *   **Request Body:** `{"price_id": "price_1PqY1f2eZvKYlo2Cg9Xy0Z1A"}`
3.  **Response:** The backend returns a Stripe Checkout Session URL.
4.  **Redirection:** The frontend uses the returned URL to launch the device's default web browser (or a custom tab) to the Stripe Checkout page.

### B. Backend (Rust/Axum)

1.  **Endpoint:** `POST /api/v1/stripe/create-checkout-session`
2.  **Authentication:** The JWT is validated to ensure the user is logged in and to retrieve the `user_id`.
3.  **Stripe API Call:** The backend calls the Stripe API to create a new Checkout Session:
    *   `stripe.checkout.sessions.create({ ... })`
    *   The session is configured with:
        *   `line_items`: The selected `price_id`.
        *   `mode`: `'subscription'`.
        *   `success_url`: `https://pocketforge.app/upgrade/success?session_id={CHECKOUT_SESSION_ID}`
        *   `cancel_url`: `https://pocketforge.app/upgrade/cancel`
        *   `client_reference_id`: The PocketForge `user_id`.
4.  **Response:** The backend returns the `session.url` to the frontend.

### C. Webhook Handling (Critical for State Management)

1.  **Stripe Webhook:** A dedicated webhook endpoint is required to handle asynchronous events from Stripe.
    *   **Endpoint:** `POST /api/v1/stripe/webhook`
2.  **Event Processing:** The backend listens for the `checkout.session.completed` event.
3.  **User Update:** Upon receiving a successful `checkout.session.completed` event, the backend:
    *   Verifies the event signature.
    *   Extracts the `client_reference_id` (which is the PocketForge `user_id`).
    *   Updates the corresponding user record in the Supabase database, setting `is_pro = TRUE`.
    *   The user's JWT will now reflect their Pro status on subsequent logins or token refreshes.

**Note:** The backend must also handle other critical webhooks like `invoice.payment_failed` and `customer.subscription.deleted` to downgrade the user's `is_pro` status when necessary.
