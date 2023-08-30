package com.saulmm.feature.challenges.model.remote.api

import com.saulmm.feature.challenges.model.remote.api.entities.AuthoredChallengeDto
import com.saulmm.feature.challenges.model.remote.api.entities.ChallengeDetailDto
import com.saulmm.feature.challenges.model.remote.api.entities.CodewarsResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CodewarsApi {

    @GET("/api/v1/users/{userName}/code-challenges/authored")
    suspend fun authoredChallenges(
        @Path("userName") userName: String,
    ): CodewarsResponseDto<AuthoredChallengeDto>

    @GET("/api/v1/code-challenges/{challengeId}")
    suspend fun challenge(
        @Path("challengeId") challengeId: String,
    ): ChallengeDetailDto
}