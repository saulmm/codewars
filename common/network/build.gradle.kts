import com.saulmm.codewars.buildsrc.BuildConstants

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.org.jetbrains.kotlin.jvm.get().pluginId)
    id(libs.plugins.org.jetbrains.kotlin.kapt.get().pluginId)
}

java {
    sourceCompatibility = BuildConstants.JAVA_VERSION
    targetCompatibility = BuildConstants.JAVA_VERSION
}

dependencies {
    implementation(project(":entity"))
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.loggin)
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)
}