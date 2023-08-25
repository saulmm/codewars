package com.saulmm.codewars.feature.home.model

import com.saulmm.codewars.entity.Challenge
import javax.inject.Inject

class AuthoredChallengesRepository @Inject constructor(
    private val remote: AuthoredChallengesDataSource
) {

    suspend fun getFrom(userName: String): List<Challenge> {
        return remote.authoredChallenges(userName)
    }
}