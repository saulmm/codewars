package com.saulmm.codewars.repository

interface Repository<Input, Output> {
    suspend fun get(query: Input): Output?
}