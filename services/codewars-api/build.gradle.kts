@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("java-library")
    id(libs.plugins.org.jetbrains.kotlin.jvm.get().pluginId)
    id(libs.plugins.org.jetbrains.kotlin.kapt.get().pluginId)
}


java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":entity"))
    implementation(project(":common:network"))
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.hilt.core)
    implementation(libs.okhttp.core)
    kapt(libs.hilt.compiler)
}