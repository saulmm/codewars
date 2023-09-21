package com.saulmm.codewars.feature.challenges.ui.authored

sealed class AuthoredChallengeEvent {
    data class NavigateToKataDetail(val kataId: String): AuthoredChallengeEvent()
    object NavigateToSettings: AuthoredChallengeEvent()
    data class NavigateToSearch(val username: String): AuthoredChallengeEvent()
}
