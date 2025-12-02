# App Store and Play Store Submission Checklist

## A. App Store (iOS/iPadOS/macOS)

| Step | Status | Notes |
| :--- | :--- | :--- |
| **1. Technical Preparation** | [ ] | |
| Configure Bundle IDs and Provisioning Profiles | [ ] | For all targets (iOS, iPadOS, macOS Catalyst). |
| Finalize `Info.plist` | [ ] | Ensure correct permissions (e.g., Network, Background Fetch). |
| Build Release Archive | [ ] | Use Xcode to create the final `.ipa` and `.app` files. |
| **2. App Store Connect** | [ ] | |
| Create App Record | [ ] | Set primary language, bundle ID, and platform availability. |
| Pricing and Availability | [ ] | Set Free/Paid tier and territory availability. |
| **3. Metadata & Assets** | [ ] | |
| App Name (PocketForge) | [ ] | Max 30 characters. |
| Subtitle | [ ] | Max 30 characters (e.g., "Code, Build, Deploy on the Go"). |
| Keywords | [ ] | Max 100 characters (e.g., "coding, ide, terminal, docker, rust, kotlin, mobile"). |
| Description | [ ] | Compelling copy highlighting key features (Terminal, AI Agent, Offline). |
| **4. Screenshots & Preview** | [ ] | |
| iPhone Screenshots (6.5-inch & 5.5-inch) | [ ] | 5-10 images showing Terminal, Git, and AI features. |
| iPad Screenshots (12.9-inch) | [ ] | 5-10 images. |
| macOS Screenshots (Catalyst) | [ ] | 5-10 images. |
| App Preview Video (Optional) | [ ] | 15-30 seconds. |
| **5. Review & Compliance** | [ ] | |
| Demo Account | [ ] | Provide login credentials for the reviewer (GitHub OAuth). |
| Contact Information | [ ] | Support URL, Marketing URL, Privacy Policy URL. |
| Export Compliance | [ ] | Confirm encryption usage. |
| Content Rights | [ ] | Confirm ownership of all assets. |
| Final Review Submission | [ ] | Submit for review. |

## B. Google Play Store (Android)

| Step | Status | Notes |
| :--- | :--- | :--- |
| **1. Technical Preparation** | [ ] | |
| Generate Signed APK/AAB | [ ] | Use Gradle to build the final Android App Bundle (`.aab`). |
| KeyStore Management | [ ] | Securely store the signing key. |
| **2. Google Play Console** | [ ] | |
| Create App Listing | [ ] | Set default language and app type (Application). |
| **3. Store Listing Metadata** | [ ] | |
| App Name (PocketForge) | [ ] | Max 50 characters. |
| Short Description | [ ] | Max 80 characters (e.g., "Full-stack mobile IDE with Docker and AI"). |
| Full Description | [ ] | Max 4000 characters. |
| **4. Assets** | [ ] | |
| App Icon (512x512, 32-bit PNG) | [ ] | Use the generated SVG (exported to PNG). |
| Feature Graphic (1024x500) | [ ] | High-impact marketing image. |
| Screenshots | [ ] | 8 images (min 2 for phone, 2 for tablet). |
| **5. Compliance & Release** | [ ] | |
| Content Rating | [ ] | Complete the content rating questionnaire. |
| Target Audience & Content | [ ] | Define age group and confirm no inappropriate content. |
| Privacy Policy | [ ] | Link to the external privacy policy. |
| Release Management | [ ] | Upload AAB to internal, closed, or open track. |
| Rollout | [ ] | Start the production rollout. |
