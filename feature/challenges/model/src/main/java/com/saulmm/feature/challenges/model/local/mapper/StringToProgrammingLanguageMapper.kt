package com.saulmm.feature.challenges.model.local.mapper

import com.saulmm.codewars.entity.ProgrammingLanguage
import com.saulmm.codewars.entity.toProgrammingLanguageOrUnknown

fun String.toProgrammingLanguageList(): List<ProgrammingLanguage> {
    return split(",")
        .map(String::uppercase)
        .map(String::toProgrammingLanguageOrUnknown)
}