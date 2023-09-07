package com.saulmm.codewars.feature.challenges.ui.authored

sealed class AuthoredChallengeEvent {
    data class NavigateToKataDetail(val kataId: String): AuthoredChallengeEvent()
    object NavigateToSettings: AuthoredChallengeEvent()
    object NavigateToSearch: AuthoredChallengeEvent()
}
