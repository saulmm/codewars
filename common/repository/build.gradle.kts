import com.saulmm.codewars.buildsrc.BuildConstants

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("java-library")
    id(libs.plugins.org.jetbrains.kotlin.jvm.get().pluginId)
}

java {
    sourceCompatibility = BuildConstants.JAVA_VERSION
    targetCompatibility = BuildConstants.JAVA_VERSION
}

dependencies {
    testImplementation(libs.truth)
    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
}