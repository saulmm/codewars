package com.saulmm.codewars.feature.challenges.model.local.mapper

import com.saulmm.codewars.entity.Challenge
import com.saulmm.codewars.feature.challenges.model.local.entities.ChallengePreviewDbo

fun Challenge.toDbo(
    userName: String,
    insertedAt: Long,
): ChallengePreviewDbo {
    return ChallengePreviewDbo(
        id = id,
        name = name,
        shortDescription = description,
        rankName = rank.name,
        tags = tags.joinToString(separator = ","),
        languages = languages.joinToString(separator = ",") { it.name },
        username = userName,
        insertedAt = insertedAt
    )
}