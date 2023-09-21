package com.saulmm.feature.challenges.model.local.mapper

import com.saulmm.codewars.entity.Challenge
import com.saulmm.feature.challenges.model.local.database.entities.ChallengePreviewDbo

internal fun Challenge.toDbo(
    username: String,
    insertedAt: Long,
): ChallengePreviewDbo {
    return ChallengePreviewDbo(
        id = id,
        name = name,
        shortDescription = description,
        tags = tags.joinToString(separator = ","),
        languages = languages.joinToString(separator = ",") { it.name },
        username = username,
        insertedAt = insertedAt
    )
}