package com.saulmm.codewars.feature.challenges.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.saulmm.codewars.feature.challenges.model.local.entities.ChallengeDetailDbo

@Dao
interface ChallengeDetailDao {

    @Query("SELECT * from challenge_detail where id = :id")
    suspend fun getById(id: String): ChallengeDetailDbo

    @Insert
    suspend fun insert(challenge: ChallengeDetailDbo)

    @Query("SELECT MAX(insertedAt) FROM challenge_preview WHERE username = :username")
    suspend fun getMostRecentInsertedDate(username: String): Long
}