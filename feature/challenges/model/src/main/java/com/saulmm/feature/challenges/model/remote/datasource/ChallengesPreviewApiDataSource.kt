package com.saulmm.feature.challenges.model.remote.datasource

import com.saulmm.codewars.entity.Challenge
import com.saulmm.feature.challenges.model.params.ChallengePreviewParams
import com.saulmm.feature.challenges.model.remote.mapper.toChallenge
import com.saulmm.codewars.repository.ReadableDataSource
import com.saulmm.feature.challenges.model.remote.api.CodewarsApi
import com.saulmm.feature.challenges.model.remote.api.entities.AuthoredChallengeDto
import javax.inject.Inject

internal class ChallengesPreviewApiDataSource @Inject constructor(
    private val codewarsApi: CodewarsApi,
) : ReadableDataSource<ChallengePreviewParams, List<Challenge>> {

    override suspend fun getData(query: ChallengePreviewParams): List<Challenge>? {
        return when (query) {
            is ChallengePreviewParams.ByUsernameAndTextQuery -> {
                throw UnsupportedOperationException("Searching by text query is not supported for remote requests")
            }
            is ChallengePreviewParams.ByUsername -> challengesByUsername(query)
        }
    }

    private suspend fun challengesByUsername(query: ChallengePreviewParams.ByUsername): List<Challenge> {
        return codewarsApi.authoredChallenges(userName = query.username)
            .data
            .map(AuthoredChallengeDto::toChallenge)
    }
}