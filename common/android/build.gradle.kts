@file:Suppress("UnstableApiUsage")

plugins {
    id("base-android-module")
}

android {
    namespace = "com.saulmm.codewars.common.android"

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.activity.compose)
    implementation(libs.compose.foundation)
    implementation(platform(libs.compose.bom))
}