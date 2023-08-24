package com.saulmm.codewars.feature.home.ui

sealed class AuthoredChallengeEvent {
    data class NavigateToKataDetail(val kataId: String): AuthoredChallengeEvent()
}
