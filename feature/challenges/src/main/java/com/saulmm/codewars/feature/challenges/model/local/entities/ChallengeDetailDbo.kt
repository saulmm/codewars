package com.saulmm.codewars.feature.challenges.model.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "challenge_detail")
class ChallengeDetailDbo(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val rankName: String,
    val tags: String,
    val languages: String,
    val url: String?,
    val stars: Int,
    val voteScore: Int,
    val insertedAt: Long = 0
)