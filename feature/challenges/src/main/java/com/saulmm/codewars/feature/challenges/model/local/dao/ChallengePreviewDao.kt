package com.saulmm.codewars.feature.challenges.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.saulmm.codewars.feature.challenges.model.local.entities.ChallengePreviewDbo

@Dao
interface ChallengePreviewDao {

    @Query("SELECT * from challenge_preview where username = :username")
    suspend fun getAllByUserName(username: String): List<ChallengePreviewDbo>

    @Insert
    suspend fun insertAll(vararg challenges: ChallengePreviewDbo)

    @Query("SELECT MAX(insertedAt) FROM challenge_preview")
    suspend fun getMostRecentInsertedDate(): Long
}