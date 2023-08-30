package com.saulmm.feature.challenges.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saulmm.feature.challenges.model.local.dao.ChallengeDetailDao
import com.saulmm.feature.challenges.model.local.dao.ChallengePreviewDao
import com.saulmm.feature.challenges.model.local.entities.ChallengeDetailDbo
import com.saulmm.feature.challenges.model.local.entities.ChallengePreviewDbo

@Database(
    entities = [
        ChallengeDetailDbo::class,
        ChallengePreviewDbo::class,
    ],
    version = 1
)
internal abstract class ChallengeDatabase: RoomDatabase() {
    abstract fun challengePreviewDao(): ChallengePreviewDao
    abstract fun challengeDetailDao(): ChallengeDetailDao

    companion object {
        const val NAME = "challenges"
    }
}