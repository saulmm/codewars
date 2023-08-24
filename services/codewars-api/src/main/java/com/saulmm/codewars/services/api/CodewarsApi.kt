package com.saulmm.codewars.services.api

import com.saulmm.codewars.services.api.dto.AuthoredChallengeDto
import com.saulmm.codewars.services.api.dto.CodeChallengeDto
import com.saulmm.codewars.services.api.dto.CodewarsResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CodewarsApi {

    @GET("/api/v1/users/{userName}/code-challenges/authored")
    suspend fun authoredChallenges(
        @Path("userName") userName: String,
    ): CodewarsResponseDto<AuthoredChallengeDto>

    @GET("/api/v1/code-challenges/{challengeId}")
    suspend fun challenge(
        @Path("challengeId") challengeId: String,
    ): CodewarsResponseDto<CodeChallengeDto>
}