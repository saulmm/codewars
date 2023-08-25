package com.saulmm.codewars.feature.home.model.mapper

import com.saulmm.codewars.entity.Kata
import com.saulmm.codewars.entity.rankValueOfOrUnknown
import com.saulmm.codewars.entity.toProgrammingLanguageOrUnknown
import com.saulmm.codewars.services.api.dto.AuthoredChallengeDto

fun AuthoredChallengeDto.toKata(): Kata {
    val truncatedDescription = description.lineSequence()
        .take(MAX_DESCRIPTION_LINES)
        .joinToString("\n")

    return Kata(
        id = id,
        name = name,
        description = truncatedDescription,
        rank = rankValueOfOrUnknown(rankName),
        tags = tags,
        languages = languages.map(String::toProgrammingLanguageOrUnknown)
    )
}

private const val MAX_DESCRIPTION_LINES = 4