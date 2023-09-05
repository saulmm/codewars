package com.saulmm.codewars.feature.challenges.ui.authored

import com.saulmm.codewars.entity.Challenge

sealed class AuthoredChallengesViewState {
    object Idle: AuthoredChallengesViewState()
    data class Loading(val username: String): AuthoredChallengesViewState()
    data class Failure(val username: String): AuthoredChallengesViewState()
    data class Loaded(
        val username: String,
        val katas: List<Challenge>
    ): AuthoredChallengesViewState()

}