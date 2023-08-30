@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.org.jetbrains.kotlin.kapt.get().pluginId)
    id(libs.plugins.hilt.get().pluginId)
    id("base-android-module")
}

android {
    namespace = "com.saulmm.feature.challenges.model"

    defaultConfig {
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true"
                )
            }
        }

    }
}

dependencies {
    implementation(project(":entity"))
    implementation(project(":common"))
    implementation(project(":common:network"))
    implementation(project(":common:repository"))
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.hilt.core)
    implementation(libs.okhttp.core)
    implementation(libs.room.ktx)
    implementation(libs.hilt.android)
    kapt(libs.room.compiler)
    kapt(libs.hilt.compiler)
}