package com.saulmm.codewars.feature.home.model.mapper

import com.saulmm.codewars.common.extensions.toURIOrNull
import com.saulmm.codewars.entity.ChallengeDetail
import com.saulmm.codewars.entity.rankValueOfOrUnknown
import com.saulmm.codewars.entity.toProgrammingLanguageOrUnknown
import com.saulmm.codewars.services.api.dto.ChallengeDetailDto

internal fun ChallengeDetailDto.toChallengeDetail(): ChallengeDetail {
    return ChallengeDetail(
        id = id,
        name = name,
        description = description,
        rank = rankValueOfOrUnknown(rank.name),
        tags = listOf(),
        languages = languages.map(String::toProgrammingLanguageOrUnknown),
        url = url.toURIOrNull(),
        stars = totalStars,
        voteScore = voteScore
    )
}