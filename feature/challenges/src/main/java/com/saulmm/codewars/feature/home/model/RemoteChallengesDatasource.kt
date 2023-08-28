package com.saulmm.codewars.feature.home.model

import com.saulmm.codewars.entity.Challenge
import com.saulmm.codewars.entity.ChallengeDetail
import com.saulmm.codewars.feature.home.model.mapper.toKata
import com.saulmm.codewars.services.api.CodewarsApi
import com.saulmm.codewars.services.api.dto.AuthoredChallengeDto
import javax.inject.Inject

class RemoteChallengesDatasource @Inject constructor(
    private val codewarsApi: CodewarsApi,
): ChallengesDatasource {


    override suspend fun authoredChallenges(userName: String): List<Challenge> {
        return codewarsApi.authoredChallenges(userName = userName).data
            .map(AuthoredChallengeDto::toKata)
    }

    override suspend fun challengeDetail(challengeId: String): ChallengeDetail {
        TODO("Not yet implemented")
    }
}