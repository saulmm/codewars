package com.saulmm.feature.challenges.model.params

sealed class ChallengePreviewParams {
    data class ByUsername(val userName: String): ChallengePreviewParams()
    data class ByTextQuery(val query: String): ChallengePreviewParams()
}