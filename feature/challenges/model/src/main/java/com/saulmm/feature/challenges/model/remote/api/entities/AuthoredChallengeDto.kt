package com.saulmm.feature.challenges.model.remote.api.entities

data class AuthoredChallengeDto(
    val id: String,
    val name: String,
    val description: String,
    val rankName: String,
    val tags: List<String>,
    val languages: List<String>
)
