plugins {
    id("com.android.application")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")

}

android {
    namespace = "com.saulmm.codewars"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.saulmm.codewars"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":entity"))
    implementation(project(":common"))
    implementation(project(":common:network"))
    implementation(project(":common:navigation-contract"))
    implementation(project(":services:codewars-api"))
    implementation(project(":feature:challenges"))
    implementation(project(":feature:kata-detail"))
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.viewmodel.ktx)
    implementation(libs.viewmodel.compose)
    implementation(libs.material)
    implementation(libs.navigation.compose)
    implementation(libs.timber)
    kapt(libs.hilt.compiler)

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    testImplementation(libs.truth)
    testImplementation(libs.junit)
    testImplementation(libs.mockito)
    testImplementation(libs.turbine)
}