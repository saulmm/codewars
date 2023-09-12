package com.saulmm.feature.challenges.model.params

sealed class ChallengePreviewParams {
    data class ByUsername(val username: String): ChallengePreviewParams()
    data class ByUsernameAndTextQuery(
        val username: String,
        val textQuery: String
    ): ChallengePreviewParams()
}