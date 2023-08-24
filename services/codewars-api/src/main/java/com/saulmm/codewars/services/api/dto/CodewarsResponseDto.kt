package com.saulmm.codewars.services.api.dto

data class CodewarsResponseDto<T>(
    val totalPages: Int? = null,
    val totalItems: Int? = null,
    val data: List<T>
)
