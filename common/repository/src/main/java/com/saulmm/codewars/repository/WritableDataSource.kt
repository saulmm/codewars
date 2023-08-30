package com.saulmm.codewars.repository

import java.util.Date

interface WritableDataSource<T, S> {
    suspend fun saveData(query: T, data: S)
    suspend fun lastSavedDataDate(query: T): Date?
}