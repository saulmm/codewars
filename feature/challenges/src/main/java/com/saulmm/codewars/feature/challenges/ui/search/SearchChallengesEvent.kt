package com.saulmm.codewars.feature.challenges.ui.search

sealed class SearchChallengesEvent {
    data class NavigateToChallengeDetail(val challengeId: String): SearchChallengesEvent()
    object NavigateBack: SearchChallengesEvent()

}