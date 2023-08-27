package com.saulmm.codewars.feature.home.model

import com.saulmm.codewars.entity.Challenge

interface AuthoredChallengesDataSource {

    suspend fun authoredChallenges(userName: String): List<Challenge>
}