import com.saulmm.codewars.buildsrc.BuildConstants

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.androidApplication.get().pluginId)
    id(libs.plugins.org.jetbrains.kotlin.kapt.get().pluginId)
    id(libs.plugins.kotlinAndroid.get().pluginId)
    id(libs.plugins.hilt.get().pluginId)
}

android {
    namespace = "com.saulmm.codewars"
    compileSdk = BuildConstants.COMPILE_SDK

    defaultConfig {
        applicationId = "com.saulmm.codewars"
        minSdk = BuildConstants.MIN_SDK
        targetSdk = BuildConstants.TARGET_SDK
        versionCode = BuildConstants.VERSION_CODE
        versionName = BuildConstants.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = BuildConstants.JAVA_VERSION
        targetCompatibility = BuildConstants.JAVA_VERSION
    }
    kotlinOptions {
        jvmTarget = BuildConstants.JAVA_VERSION.toString()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = BuildConstants.KOTLIN_COMPILER_EXTENSION_VERSION
    }
}

dependencies {
    implementation(project(":entity"))
    implementation(project(":common"))
    implementation(project(":common:network"))
    implementation(project(":common:android:repository"))
    implementation(project(":common:navigation-contract"))
    implementation(project(":common:codewars-design-system"))
    implementation(project(":feature:challenges"))
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
    kapt(libs.hilt.compiler)

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    testImplementation(libs.truth)
    testImplementation(libs.junit)
    testImplementation(libs.mockito)
    testImplementation(libs.turbine)
}