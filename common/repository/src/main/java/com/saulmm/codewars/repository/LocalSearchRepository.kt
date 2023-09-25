package com.saulmm.codewars.repository

open class LocalSearchRepository<Input, Output>(
    private val local: ReadAndWriteDataSource<Input, Output>
) {

    suspend fun require(query: Input): Output {
        return checkNotNull(get(query))
    }

    suspend fun get(query: Input): Output? {
        return local.getData(query)
    }

}