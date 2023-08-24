package com.saulmm.codewars.services.api.dto

import com.saulmm.codewars.entity.Rank

data class AuthoredChallengeDto(
    val id: String,
    val name: String,
    val description: String,
    val rankName: String,
    val tags: List<String>,
    val languages: List<String>
)
