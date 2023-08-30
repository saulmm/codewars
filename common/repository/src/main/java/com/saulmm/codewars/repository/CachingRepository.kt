package com.saulmm.codewars.repository

import java.util.Date
import java.util.concurrent.TimeUnit

open class CachingRepository<Input, Output>(
    private val remote: ReadableDataSource<Input, Output>,
    private val local: ReadAndWriteDataSource<Input, Output>
): Repository<Input, Output> {

    private suspend fun lastSavedIsValid(): Boolean {
        val time = local.lastSavedDataDate()?.time
        println("Time: $time")
        return time
            ?.let { lastSavedTime -> (Date().time - lastSavedTime) <= FOUR_HOURS_MILLIS }
            ?: false
    }

    override suspend fun get(query: Input): Output? {
        val localData = local.getData(query)
            ?.takeIf { lastSavedIsValid() }

        return localData ?: remote.getData(query)
            ?.also { local.saveData(query, it) }
    }

    private companion object{
        val FOUR_HOURS_MILLIS = TimeUnit.HOURS.toMillis(4)
    }
}