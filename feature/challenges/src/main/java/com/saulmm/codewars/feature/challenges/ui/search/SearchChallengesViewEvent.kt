package com.saulmm.codewars.feature.challenges.ui.search

sealed class SearchChallengesViewEvent {
    object OnBackPressed: SearchChallengesViewEvent()
    data class OnTextQueryChanged(val query: String): SearchChallengesViewEvent()
    data class OnChallengeClick(val id: String): SearchChallengesViewEvent()
    object OnFailureTryAgainClick: SearchChallengesViewEvent()
}