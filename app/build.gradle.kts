plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.omshivgoraksha"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.omshivgoraksha"
        minSdk = 23
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

//dependencies {
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.activity.compose)
//    implementation(libs.androidx.compose.material3)
//    implementation(libs.androidx.compose.ui)
//    implementation(libs.androidx.compose.ui.graphics)
//    implementation(libs.androidx.compose.ui.tooling.preview)
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    testImplementation(libs.junit)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(libs.androidx.junit)
//    debugImplementation(libs.androidx.compose.ui.test.manifest)
//    debugImplementation(libs.androidx.compose.ui.tooling)
//
//
//        // Core
//        implementation("androidx.core:core-ktx:1.15.0")
//        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
//
//        // Compose
//        implementation(platform("androidx.compose:compose-bom:2025.01.00"))
//        implementation("androidx.compose.ui:ui")
//        implementation("androidx.compose.ui:ui-tooling-preview")
//        implementation("androidx.compose.material3:material3")
//
//        // Activity
//        implementation("androidx.activity:activity-compose:1.10.1")
//
//        // Navigation
//        implementation("androidx.navigation:navigation-compose:2.8.5")
//
//        // ViewModel
//        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
//
//        // Splash Screen
//        implementation("androidx.core:core-splashscreen:1.0.1")
//
//        // Retrofit
//        implementation("com.squareup.retrofit2:retrofit:2.11.0")
//        implementation("com.squareup.retrofit2:converter-gson:2.11.0")
//
//        // OkHttp
//        implementation("com.squareup.okhttp3:okhttp:4.12.0")
//        implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
//
//        // DataStore
//        implementation("androidx.datastore:datastore-preferences:1.1.1")
//
//        // Coroutines
//        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
//
//        debugImplementation("androidx.compose.ui:ui-tooling")
//}

dependencies {

    // =========================
    // Compose
    // =========================
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)

    // =========================
    // Android / Activity
    // =========================
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // =========================
    // Navigation
    // =========================
    implementation("androidx.navigation:navigation-compose:2.8.5")

    // =========================
    // ViewModel
    // =========================
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")

    // =========================
    // Splash Screen
    // =========================
    implementation("androidx.core:core-splashscreen:1.0.1")

    // =========================
    // Retrofit
    // =========================
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // =========================
    // OkHttp
    // =========================
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // =========================
    // DataStore
    // =========================
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")
    // =========================
    // Icons
    // =========================
    implementation(platform(libs.androidx.compose.bom))
    implementation("androidx.compose.material:material-icons-extended")

    // =========================
    // Coroutines
    // =========================
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

    // =========================
    // Unit Tests
    // =========================
    testImplementation(libs.junit)

    // =========================
    // Android Tests
    // =========================
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    // =========================
    // Debug
    // =========================
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}