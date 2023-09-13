package com.saulmm.codewars.feature.challenges.ui.search

import com.saulmm.codewars.entity.Challenge

sealed class SearchChallengesViewState {
    object Idle: SearchChallengesViewState()
    object Loading: SearchChallengesViewState()
    data class Loaded(val challenges: List<Challenge>): SearchChallengesViewState()
    object Failure: SearchChallengesViewState()
}