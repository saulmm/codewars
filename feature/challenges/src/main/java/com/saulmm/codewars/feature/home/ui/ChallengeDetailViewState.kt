package com.saulmm.codewars.feature.home.ui

import com.saulmm.codewars.entity.ChallengeDetail

sealed class ChallengeDetailViewState {
    object Idle: ChallengeDetailViewState()
    object Loading: ChallengeDetailViewState()
    object Failure: ChallengeDetailViewState()
    data class Loaded(val katas: ChallengeDetail): ChallengeDetailViewState()

}