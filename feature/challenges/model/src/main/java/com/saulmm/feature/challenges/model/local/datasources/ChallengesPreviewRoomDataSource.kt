package com.saulmm.feature.challenges.model.local.datasources

import com.saulmm.codewars.entity.Challenge
import com.saulmm.feature.challenges.model.local.ChallengeDatabase
import com.saulmm.feature.challenges.model.params.ChallengePreviewParams
import com.saulmm.feature.challenges.model.local.mapper.toChallengePreview
import com.saulmm.feature.challenges.model.local.mapper.toDbo
import com.saulmm.codewars.repository.ReadAndWriteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

internal class ChallengesPreviewRoomDataSource @Inject constructor(
    private val challengeDatabase: ChallengeDatabase
): ReadAndWriteDataSource<ChallengePreviewParams, List<Challenge>> {

    private val challengePreviewDao by lazy {
        challengeDatabase.challengePreviewDao()
    }

    override suspend fun getData(query: ChallengePreviewParams): List<Challenge>? = withContext(Dispatchers.IO){
        when (query) {
            is ChallengePreviewParams.ByUsernameAndTextQuery -> {
                challengesByChallengeName(query.textQuery)
            }
            is ChallengePreviewParams.ByUsername -> {
                challengesByUsername(query.username)
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
            is ChallengePreviewParams.ByUsernameAndTextQuery -> {
                // no - op
            }
            is ChallengePreviewParams.ByUsername -> {
                saveChallengesByUsername(
                    username = query.username,
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
            is ChallengePreviewParams.ByUsernameAndTextQuery -> {
                challengePreviewDao.getMostRecentInsertedDateByKeyword(query.textQuery)?.let(::Date)
            }
            is ChallengePreviewParams.ByUsername -> {
                challengePreviewDao.getMostRecentInsertedDate(query.username)?.let(::Date)
            }
        }
    }
}