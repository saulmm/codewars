package com.saulmm.codewars.feature.challenges.model

import com.saulmm.codewars.entity.Challenge
import com.saulmm.codewars.entity.ChallengeDetail
import com.saulmm.codewars.feature.challenges.model.remote.mapper.toChallenge
import com.saulmm.codewars.feature.challenges.model.remote.mapper.toChallengeDetail
import com.saulmm.codewars.services.api.CodewarsApi
import com.saulmm.codewars.services.api.dto.AuthoredChallengeDto
import javax.inject.Inject

class RemoteChallengesDatasource @Inject constructor(
    private val codewarsApi: CodewarsApi,
): ChallengesDatasource {


    override suspend fun authoredChallenges(userName: String): List<Challenge> {
        return codewarsApi.authoredChallenges(userName = userName).data
            .map(AuthoredChallengeDto::toChallenge)
    }

    override suspend fun challengeDetail(challengeId: String): ChallengeDetail {
        return codewarsApi.challenge(challengeId).toChallengeDetail()
    }
}