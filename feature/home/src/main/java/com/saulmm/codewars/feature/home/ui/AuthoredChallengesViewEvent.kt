package com.saulmm.codewars.feature.home.ui

sealed class AuthoredChallengesViewEvent {
    data class OnChallengeClick(val kataId: String): AuthoredChallengesViewEvent()
    object OnFailureTryAgainClick: AuthoredChallengesViewEvent()
}