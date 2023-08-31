package com.saulmm.feature.challenges.model.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saulmm.feature.challenges.model.local.database.entities.ChallengeDetailDbo

@Dao
internal interface ChallengeDetailDao {

    @Query("SELECT * from challenge_detail where id = :id")
    suspend fun getById(id: String): ChallengeDetailDbo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(challenge: ChallengeDetailDbo)

    @Query("SELECT insertedAt FROM challenge_detail WHERE id = :id")
    suspend fun getMostRecentInsertedDate(id: String): Long?
}