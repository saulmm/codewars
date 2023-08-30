@file:Suppress("UnstableApiUsage")
import com.saulmm.codewars.buildsrc.BuildConstants

plugins {
    id("base-android-module")
}

android {
    namespace = "com.saulmm.common.navigation_contract"

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = BuildConstants.KOTLIN_COMPILER_EXTENSION_VERSION
    }
}

dependencies {
    implementation(project(":entity"))
    implementation(libs.navigation.compose)
    testImplementation(libs.truth)
    testImplementation(libs.junit)
    testImplementation(libs.mockito)
}