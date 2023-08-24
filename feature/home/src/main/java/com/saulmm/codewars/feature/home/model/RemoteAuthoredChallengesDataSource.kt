package com.saulmm.codewars.feature.home.model

import com.saulmm.codewars.entity.Kata
import com.saulmm.codewars.feature.home.model.mapper.toKata
import com.saulmm.codewars.services.api.CodewarsApi
import com.saulmm.codewars.services.api.dto.AuthoredChallengeDto
import javax.inject.Inject

class RemoteAuthoredChallengesDataSource @Inject constructor(
    private val codewarsApi: CodewarsApi,
): AuthoredChallengesDataSource {


    override suspend fun authoredChallenges(userName: String): List<Kata> {
        return codewarsApi.authoredChallenges(userName = userName).data
            .map(AuthoredChallengeDto::toKata)
    }
}