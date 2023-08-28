package com.saulmm.codewars.entity

data class ChallengeDetail(
    val id: String,
    val name: String,
    val description: String,
    val rank: Rank,
    val tags: List<String>,
    val languages: List<ProgrammingLanguage>
)