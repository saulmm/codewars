package com.saulmm.feature.challenges.model.remote.api.entities

data class CodewarsResponseDto<T>(
    val totalPages: Int? = null,
    val totalItems: Int? = null,
    val data: List<T>
)
