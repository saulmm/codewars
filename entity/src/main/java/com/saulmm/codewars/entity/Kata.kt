package com.saulmm.codewars.entity

data class Kata(
    val id: String,
    val name: String,
    val description: String,
    val rank: Rank,
    val tags: List<String>,
    val languages: List<ProgrammingLanguage>
)