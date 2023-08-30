import com.saulmm.codewars.buildsrc.BuildConstants

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("java-library")
    id(libs.plugins.org.jetbrains.kotlin.jvm.get().pluginId)
    id(libs.plugins.org.jetbrains.kotlin.kapt.get().pluginId)
}

java {
    sourceCompatibility = BuildConstants.JAVA_VERSION
    targetCompatibility = BuildConstants.JAVA_VERSION
}

dependencies {
    implementation(project(":entity"))
    implementation(libs.hilt.core)
    implementation(libs.retrofit.moshi)
    kapt(libs.hilt.compiler)
}