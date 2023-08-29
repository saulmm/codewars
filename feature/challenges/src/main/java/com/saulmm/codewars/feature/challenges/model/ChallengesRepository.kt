package com.saulmm.codewars.feature.challenges.model

import com.saulmm.codewars.entity.Challenge
import com.saulmm.codewars.entity.ChallengeDetail
import javax.inject.Inject

class ChallengesRepository @Inject constructor(
    private val remote: ChallengesDatasource
) {

    suspend fun getFrom(userName: String): List<Challenge> {
        return remote.authoredChallenges(userName)
    }

    suspend fun challengeDetail(challengeId: String): ChallengeDetail {
        return remote.challengeDetail(challengeId)
    }
}