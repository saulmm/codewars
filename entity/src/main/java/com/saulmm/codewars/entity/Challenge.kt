package com.saulmm.codewars.entity

data class Challenge(
    val id: String,
    val name: String,
    val description: String,
    val tags: List<String>,
    val languages: List<ProgrammingLanguage>
)