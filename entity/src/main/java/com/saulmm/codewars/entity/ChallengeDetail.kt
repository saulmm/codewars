package com.saulmm.codewars.entity

import java.net.URI

data class ChallengeDetail(
    val id: String,
    val name: String,
    val description: String,
    val tags: List<String>,
    val languages: List<ProgrammingLanguage>,
    val url: URI?,
    val stars: Int,
    val voteScore: Int
)