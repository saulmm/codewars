package com.saulmm.feature.challenges.model.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.saulmm.codewars.entity.Rank

@Entity(tableName = "challenge_preview")
internal data class ChallengePreviewDbo(
    @PrimaryKey val id: String,
    val name: String,
    val shortDescription: String,
    val rankName: String,
    val tags: String,
    val languages: String,
    val username: String,
    val insertedAt: Long = 0
)