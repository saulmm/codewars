package com.saulmm.codewars.feature.challenges.ui.authored

sealed class AuthoredChallengesViewEvent {
    data class OnChallengeClick(val kataId: String): AuthoredChallengesViewEvent()
    object OnFailureTryAgainClick: AuthoredChallengesViewEvent()
    object OnSettingsClick: AuthoredChallengesViewEvent()
    object OnSearchClick: AuthoredChallengesViewEvent()
    data class OnSearchQuerySelected(val query: String): AuthoredChallengesViewEvent()
}