package com.saulmm.codewars.feature.home.ui

import com.saulmm.codewars.entity.Kata

sealed class AuthoredChallengesViewEvent {
    data class OnChallengeClick(val kataId: String): AuthoredChallengesViewEvent()
}