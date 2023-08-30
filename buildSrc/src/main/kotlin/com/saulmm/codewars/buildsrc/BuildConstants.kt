package com.saulmm.codewars.buildsrc

import org.gradle.api.JavaVersion

object BuildConstants {
    const val MIN_SDK = 24
    const val TARGET_SDK = 33
    const val COMPILE_SDK = 33
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"
    val JAVA_VERSION = JavaVersion.VERSION_17
    val KOTLIN_COMPILER_EXTENSION_VERSION = "1.4.7"
}
