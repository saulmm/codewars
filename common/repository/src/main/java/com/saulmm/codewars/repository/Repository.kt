package com.saulmm.codewars.repository

interface Repository<Input, Output> {
    suspend fun get(query: Input): Output?
    suspend fun require(query: Input): Output {
        return checkNotNull(get(query))
    }
}