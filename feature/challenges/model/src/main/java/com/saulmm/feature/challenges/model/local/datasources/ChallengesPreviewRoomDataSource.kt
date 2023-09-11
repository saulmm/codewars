package com.saulmm.feature.challenges.model.local.datasources

import com.saulmm.codewars.entity.Challenge
import com.saulmm.feature.challenges.model.local.ChallengeDatabase
import com.saulmm.feature.challenges.model.params.ChallengePreviewParams
import com.saulmm.feature.challenges.model.local.mapper.toChallengePreview
import com.saulmm.feature.challenges.model.local.mapper.toDbo
import com.saulmm.codewars.repository.ReadAndWriteDataSource
import java.util.Date
import javax.inject.Inject

internal class ChallengesPreviewRoomDataSource @Inject constructor(
    private val challengeDatabase: ChallengeDatabase
): ReadAndWriteDataSource<ChallengePreviewParams, List<Challenge>> {

    private val challengePreviewDao by lazy {
        challengeDatabase.challengePreviewDao()
    }

    override suspend fun getData(query: ChallengePreviewParams): List<Challenge>? {
        return when (query) {
            is ChallengePreviewParams.ByTextQuery -> {
                challengesByChallengeName(query.query)
            }
            is ChallengePreviewParams.ByUsername -> {
                challengesByUsername(query.userName)
            }
        }

    }

    private fun challengesByChallengeName(challengeName: String): List<Challenge> {
        return challengePreviewDao.searchByName(challengeName)
            .map { it.toChallengePreview() }
    }

    private suspend fun challengesByUsername(username: String): List<Challenge> {
        return challengePreviewDao.getAllByUserName(username)
            .map { it.toChallengePreview() }
    }

    override suspend fun saveData(query: ChallengePreviewParams, data: List<Challenge>) {
        when (query) {
            is ChallengePreviewParams.ByTextQuery -> {
                // no - op
            }
            is ChallengePreviewParams.ByUsername -> {
                saveChallengesByUsername(
                    username = query.userName,
                    data = data
                )
            }
        }
    }

    private suspend fun saveChallengesByUsername(username: String, data: List<Challenge>) {
        val dbos = data.map {
            it.toDbo(
                username = username,
                insertedAt = Date().time
            )
        }

        challengePreviewDao.insertAll(
            *dbos.toTypedArray()
        )
    }

    override suspend fun lastSavedDataDate(query: ChallengePreviewParams): Date? {
        return when (query) {
            is ChallengePreviewParams.ByTextQuery -> {
                challengePreviewDao.getMostRecentInsertedDateByKeyword(query.query)?.let(::Date)
            }
            is ChallengePreviewParams.ByUsername -> {
                challengePreviewDao.getMostRecentInsertedDate(query.userName)?.let(::Date)
            }
        }
    }
}