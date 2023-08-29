package com.saulmm.codewars.repository

interface ReadableDataSource<T, Q> {
    suspend fun getData(query: T): Q?
}