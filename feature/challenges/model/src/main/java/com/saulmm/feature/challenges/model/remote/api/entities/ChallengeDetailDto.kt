package com.saulmm.feature.challenges.model.remote.api.entities

data class ChallengeDetailDto(
    val id: String,
    val name: String,
    val languages: List<String>,
    val url: String,
    val createdBy: UserDto,
    val description: String,
    val totalStars: Int,
    val voteScore: Int,
    val tags: List<String>,
)

data class UserDto(
    val username: String,
    val url: String
)
