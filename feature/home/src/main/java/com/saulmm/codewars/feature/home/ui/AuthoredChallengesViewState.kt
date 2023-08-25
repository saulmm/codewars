package com.saulmm.codewars.feature.home.ui

import com.saulmm.codewars.entity.Challenge

sealed class AuthoredChallengesViewState {
    object Idle: AuthoredChallengesViewState()
    object Loading: AuthoredChallengesViewState()
    object Failure: AuthoredChallengesViewState()
    data class Loaded(val katas: List<Challenge>): AuthoredChallengesViewState()

}