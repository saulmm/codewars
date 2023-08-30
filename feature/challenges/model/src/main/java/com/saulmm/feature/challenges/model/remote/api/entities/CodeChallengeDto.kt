package com.saulmm.feature.challenges.model.remote.api.entities

data class CodeChallengeDto(
    val name: String,
    val category: String,
    val languages: List<String>,
    val url : String,
    val createdBy: ChallengeAuthorDto,
    val description: String,
    val totalAttempts: Int,
    val totalCompleted: Int,
    val tags: List<String>
)

data class ChallengeAuthorDto(
    val username: String,
    val url: String,
)