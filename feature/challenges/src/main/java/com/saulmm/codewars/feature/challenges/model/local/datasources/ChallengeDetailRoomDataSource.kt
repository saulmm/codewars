package com.saulmm.codewars.feature.challenges.model.local.datasources

import com.saulmm.codewars.entity.ChallengeDetail
import com.saulmm.codewars.feature.challenges.model.local.ChallengeDatabase
import com.saulmm.codewars.feature.challenges.model.local.mapper.toChallengeDetail
import com.saulmm.codewars.feature.challenges.model.local.mapper.toDbo
import com.saulmm.codewars.feature.challenges.model.params.ChallengeDetailParams
import com.saulmm.codewars.repository.ReadAndWriteDataSource
import java.util.Date
import javax.inject.Inject

class ChallengeDetailRoomDataSource @Inject constructor(
    private val database: ChallengeDatabase
): ReadAndWriteDataSource<ChallengeDetailParams, ChallengeDetail> {
    private val challengeDetailDao by lazy {
        database.challengeDetailDao()
    }

    override suspend fun getData(query: ChallengeDetailParams): ChallengeDetail? {
        return challengeDetailDao.getById(query.challengeId)?.toChallengeDetail()
    }

    override suspend fun saveData(query: ChallengeDetailParams, data: ChallengeDetail) {
        challengeDetailDao.insert(
            data.toDbo(insertedAt = Date())
        )
    }

    override suspend fun lastSavedDataDate(query: ChallengeDetailParams): Date? {
        return challengeDetailDao.getMostRecentInsertedDate(query.challengeId)
            ?.let(::Date)
    }
}