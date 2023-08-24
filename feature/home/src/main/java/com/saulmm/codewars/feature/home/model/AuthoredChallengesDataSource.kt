package com.saulmm.codewars.feature.home.model

import com.saulmm.codewars.entity.Kata

interface AuthoredChallengesDataSource {

    suspend fun authoredChallenges(userName: String): List<Kata>
}