package com.saulmm.codewars.feature.challenges.model.local.datasources

import com.saulmm.codewars.entity.Challenge
import com.saulmm.codewars.feature.challenges.model.local.ChallengeDatabase
import com.saulmm.codewars.feature.challenges.model.params.ChallengePreviewParams
import com.saulmm.codewars.feature.challenges.model.local.mapper.toChallengePreview
import com.saulmm.codewars.feature.challenges.model.local.mapper.toDbo
import com.saulmm.codewars.repository.ReadAndWriteDataSource
import java.util.Date
import javax.inject.Inject

class ChallengesPreviewRoomDataSource @Inject constructor(
    private val challengeDatabase: ChallengeDatabase
): ReadAndWriteDataSource<ChallengePreviewParams, List<Challenge>> {

    private val challengePreviewDao by lazy {
        challengeDatabase.challengePreviewDao()
    }

    override suspend fun getData(query: ChallengePreviewParams): List<Challenge>? {
        return challengePreviewDao.getAllByUserName(query.userName)
            .map { it.toChallengePreview() }
    }

    override suspend fun saveData(query: ChallengePreviewParams, data: List<Challenge>) {
        val dbos = data.map {
            it.toDbo(
                userName = query.userName,
                insertedAt = Date().time
            )
        }

        challengePreviewDao.insertAll(
            *dbos.toTypedArray()
        )
    }

    override suspend fun lastSavedDataDate(query: ChallengePreviewParams): Date? {
        return challengePreviewDao.getMostRecentInsertedDate()?.let {
            Date(it)
        }
    }
}