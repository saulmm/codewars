package com.saulmm.feature.challenges.model.local.mapper

import com.saulmm.codewars.entity.ChallengeDetail
import com.saulmm.codewars.entity.ProgrammingLanguage
import com.saulmm.feature.challenges.model.local.database.entities.ChallengeDetailDbo
import java.util.Date

internal fun ChallengeDetail.toDbo(insertedAt: Date): ChallengeDetailDbo {
    return ChallengeDetailDbo(
        id = id,
        name = name,
        description = description,
        tags = tags.joinToString(separator = ","),
        languages = languages.joinToString(separator = ",", transform = ProgrammingLanguage::name),
        url = url?.toString(),
        stars = stars,
        voteScore = voteScore,
        insertedAt = insertedAt.time
    )
}