package com.saulmm.codewars.services.api.dto

import com.squareup.moshi.Json

data class ChallengeDetailDto(
    val id: String,
    val name: String,
    val slug: String,
    val category: String,
    val languages: List<String>,
    val url: String,
    val rank: RankDto,
    val createdBy: UserDto,
    val description: String,
    val totalStars: Int,
    val voteScore: Int,
    val tags: List<String>,
)

data class RankDto(
    @Json(name = "name") val name: String,
)

data class UserDto(
    val username: String,
    val url: String
)
