package com.saulmm.feature.challenges.model.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saulmm.feature.challenges.model.local.database.entities.ChallengePreviewDbo

@Dao
internal interface ChallengePreviewDao {

    @Query("SELECT * from challenge_preview where username = :username")
    suspend fun getAllByUserName(username: String): List<ChallengePreviewDbo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg challenges: ChallengePreviewDbo)

    @Query("SELECT MAX(insertedAt) FROM challenge_preview where username = :username")
    suspend fun getMostRecentInsertedDate(username: String): Long?

    @Query("SELECT MAX(insertedAt) FROM challenge_preview WHERE name LIKE '%' || :keyword || '%'")
    suspend fun getMostRecentInsertedDateByKeyword(keyword: String): Long?

    @Query("SELECT * FROM challenge_preview WHERE name LIKE '%' || :keyword || '%'")
    fun searchByName(keyword: String): List<ChallengePreviewDbo>
}