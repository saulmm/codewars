package com.saulmm.codewars.feature.home.model

import com.saulmm.codewars.entity.Challenge
import javax.inject.Inject

class ChallengesRepository @Inject constructor(
    private val remote: ChallengesDatasource
) {

    suspend fun getFrom(userName: String): List<Challenge> {
        return remote.authoredChallenges(userName)
    }
}