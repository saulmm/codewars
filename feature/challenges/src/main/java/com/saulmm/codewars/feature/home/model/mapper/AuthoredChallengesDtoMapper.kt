package com.saulmm.codewars.feature.home.model.mapper

import com.saulmm.codewars.entity.Challenge
import com.saulmm.codewars.entity.rankValueOfOrUnknown
import com.saulmm.codewars.entity.toProgrammingLanguageOrUnknown
import com.saulmm.codewars.services.api.dto.AuthoredChallengeDto

internal fun AuthoredChallengeDto.toChallenge(): Challenge {
    val truncatedDescription = description.lineSequence()
        .take(MAX_DESCRIPTION_LINES)
        .joinToString("\n")

    return Challenge(
        id = id,
        name = name,
        description = truncatedDescription,
        rank = rankValueOfOrUnknown(rankName),
        tags = tags,
        languages = languages.map(String::toProgrammingLanguageOrUnknown)
    )
}

private const val MAX_DESCRIPTION_LINES = 4