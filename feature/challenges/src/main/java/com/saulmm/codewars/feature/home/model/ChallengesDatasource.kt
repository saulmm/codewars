package com.saulmm.codewars.feature.home.model

import com.saulmm.codewars.entity.Challenge
import com.saulmm.codewars.entity.ChallengeDetail

interface ChallengesDatasource {

    suspend fun authoredChallenges(userName: String): List<Challenge>
    suspend fun challengeDetail(challengeId: String): ChallengeDetail
}