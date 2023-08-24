package com.saulmm.codewars.feature.home.model

import com.saulmm.codewars.entity.Kata
import javax.inject.Inject

class AuthoredChallengesRepository @Inject constructor(
    private val remote: AuthoredChallengesDataSource
) {

    suspend fun getFrom(userName: String): List<Kata> {
        return remote.authoredChallenges(userName)
    }
}