package com.saulmm.codewars.feature.challenges.model.local.mapper

import com.saulmm.codewars.entity.Challenge
import com.saulmm.codewars.entity.rankValueOfOrUnknown
import com.saulmm.codewars.feature.challenges.model.local.entities.ChallengePreviewDbo

fun ChallengePreviewDbo.toChallengePreview(): Challenge {
    return Challenge(
        id = id,
        name = name,
        description = shortDescription,
        rank = rankValueOfOrUnknown(rankName),
        tags = tags.split(","),
        languages = languages.toProgrammingLanguageList()
    )
}