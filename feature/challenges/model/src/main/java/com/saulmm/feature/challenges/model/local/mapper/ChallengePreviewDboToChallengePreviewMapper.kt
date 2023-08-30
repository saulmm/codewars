package com.saulmm.feature.challenges.model.local.mapper

import com.saulmm.codewars.entity.Challenge
import com.saulmm.feature.challenges.model.local.database.entities.ChallengePreviewDbo

internal fun ChallengePreviewDbo.toChallengePreview(): Challenge {
    return Challenge(
        id = id,
        name = name,
        description = shortDescription,
        tags = tags.split(","),
        languages = languages.toProgrammingLanguageList()
    )
}