package com.saulmm.codewars.feature.challenges.ui.detail

import com.saulmm.codewars.entity.ChallengeDetail

sealed class ChallengeDetailViewState {
    object Idle: ChallengeDetailViewState()
    object Loading: ChallengeDetailViewState()
    object Failure: ChallengeDetailViewState()
    data class Loaded(val challenge: ChallengeDetail): ChallengeDetailViewState()
}