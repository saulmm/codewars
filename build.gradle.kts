// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(libs.plugins.androidApplication.get().pluginId) apply false
    id(libs.plugins.kotlinAndroid.get().pluginId) apply false
    id("com.google.dagger.hilt.android") version "2.46.1" apply false
    id(libs.plugins.org.jetbrains.kotlin.kapt.get().pluginId) apply false
    id(libs.plugins.org.jetbrains.kotlin.jvm.get().pluginId) apply false
    id(libs.plugins.androidLibrary.get().pluginId) apply false
}