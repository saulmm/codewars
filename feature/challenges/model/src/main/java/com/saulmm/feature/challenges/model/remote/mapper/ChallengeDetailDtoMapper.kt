package com.saulmm.feature.challenges.model.remote.mapper

import com.saulmm.codewars.common.extensions.toURIOrNull
import com.saulmm.codewars.entity.ChallengeDetail
import com.saulmm.codewars.entity.rankValueOfOrUnknown
import com.saulmm.codewars.entity.toProgrammingLanguageOrUnknown
import com.saulmm.feature.challenges.model.remote.api.entities.ChallengeDetailDto

internal fun ChallengeDetailDto.toChallengeDetail(): ChallengeDetail {
    return ChallengeDetail(
        id = id,
        name = name,
        description = description,
        rank = rankValueOfOrUnknown(rank.name),
        tags = tags,
        languages = languages.map(String::toProgrammingLanguageOrUnknown),
        url = url.toURIOrNull(),
        stars = totalStars,
        voteScore = voteScore
    )
}