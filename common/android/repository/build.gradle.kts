@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.org.jetbrains.kotlin.kapt.get().pluginId)
    id(libs.plugins.hilt.get().pluginId)
    id("base-android-module")
}

android {
    namespace = "org.saulmm.common.android.repository"
}

dependencies {
    implementation(project(":common:repository"))
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}