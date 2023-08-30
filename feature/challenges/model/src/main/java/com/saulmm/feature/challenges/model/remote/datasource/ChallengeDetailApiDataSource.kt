package com.saulmm.feature.challenges.model.remote.datasource

import com.saulmm.codewars.entity.ChallengeDetail
import com.saulmm.feature.challenges.model.params.ChallengeDetailParams
import com.saulmm.feature.challenges.model.remote.mapper.toChallengeDetail
import com.saulmm.codewars.repository.ReadableDataSource
import com.saulmm.codewars.services.api.CodewarsApi
import javax.inject.Inject

internal class ChallengeDetailApiDataSource @Inject constructor(
    private val codewarsApi: CodewarsApi
): ReadableDataSource<ChallengeDetailParams, ChallengeDetail> {

    override suspend fun getData(query: ChallengeDetailParams): ChallengeDetail? {
        return codewarsApi.challenge(query.challengeId).toChallengeDetail()
    }
}