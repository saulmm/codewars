package com.saulmm.feature.challenges.model.local.mapper

import com.saulmm.codewars.common.extensions.toURIOrNull
import com.saulmm.codewars.entity.ChallengeDetail
import com.saulmm.codewars.entity.rankValueOfOrUnknown
import com.saulmm.feature.challenges.model.local.database.entities.ChallengeDetailDbo

internal fun ChallengeDetailDbo.toChallengeDetail(): ChallengeDetail {
    return ChallengeDetail(
        id = id,
        name = name,
        description = description,
        rank = rankValueOfOrUnknown(rankName),
        tags = tags.split(","),
        languages = languages.toProgrammingLanguageList(),
        url = url?.toURIOrNull(),
        stars = stars,
        voteScore = voteScore
    )
}