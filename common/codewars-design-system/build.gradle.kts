@file:Suppress("UnstableApiUsage")

import com.saulmm.codewars.buildsrc.BuildConstants

plugins {
    id("base-android-module")
}

android {
    namespace = "com.saulmm.codewars.common.design.system"

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion =
            BuildConstants.KOTLIN_COMPILER_EXTENSION_VERSION
    }
}

dependencies {
    implementation(project(":entity"))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.core.ktx)
    implementation(libs.accompanist.placeholder)
    implementation(platform(libs.compose.bom))
}