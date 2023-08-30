import com.saulmm.codewars.buildsrc.BuildConstants


@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.org.jetbrains.kotlin.kapt.get().pluginId)
    id(libs.plugins.hilt.get().pluginId)
    id("base-android-module")
}

android {
    namespace = "com.saulmm.codewars.feature.challenges"

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = BuildConstants.KOTLIN_COMPILER_EXTENSION_VERSION
    }
}

dependencies {
    implementation(project(":entity"))
    implementation(project(":common"))
    implementation(project(":common:android"))
    implementation(project(":common:network"))
    implementation(project(":common:repository"))
    implementation(project(":common:codewars-design-system"))
    implementation(project(":feature:challenges:model"))
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
    implementation(libs.richtext)
    implementation(libs.richtext.m3)

    kapt(libs.hilt.compiler)

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    testImplementation(libs.truth)
    testImplementation(libs.junit)
    testImplementation(libs.mockito)
    testImplementation(libs.turbine)
    testImplementation(libs.coroutines.test)
}