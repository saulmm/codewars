package com.saulmm.codewars.entity

fun String.toProgrammingLanguageOrUnknown(): ProgrammingLanguage {
    return runCatching { enumValueOf<ProgrammingLanguage>(this.uppercase()) }
        .getOrElse { ProgrammingLanguage.UNKNOWN }
}