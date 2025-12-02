buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    dependencies {
        classpath(libs.plugins.kotlinMultiplatform)
        classpath(libs.plugins.androidApplication)
        classpath(libs.plugins.compose)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

// Define versions for the root project
val kotlinVersion: String by project
val agpVersion: String by project
val composeVersion: String by project

// Placeholder for dependency versions (real versions are in frontend/gradle/libs.versions.toml)
// This is mainly for IDE sync and to avoid errors in the root build file.
project.extra.set("kotlinVersion", "2.0.0")
project.extra.set("agpVersion", "8.2.0")
project.extra.set("composeVersion", "1.6.10")
