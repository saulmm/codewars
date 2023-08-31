package com.saulmm.codewars.buildsrc

import org.gradle.api.JavaVersion

object BuildConstants {
    const val MIN_SDK = 24
    const val TARGET_SDK = 33
    const val COMPILE_SDK = 34
    const val VERSION_CODE = 2
    const val VERSION_NAME = "1.0.1"
    val JAVA_VERSION = JavaVersion.VERSION_11
    val KOTLIN_COMPILER_EXTENSION_VERSION = "1.4.7"
}
