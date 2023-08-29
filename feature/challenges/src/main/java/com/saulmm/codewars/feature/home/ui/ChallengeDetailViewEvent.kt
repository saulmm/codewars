package com.saulmm.codewars.feature.home.ui

import java.net.URI

sealed class ChallengeDetailViewEvent {
    data class OnUrlChipClick(val uri: URI): ChallengeDetailViewEvent()
    object OnScoreClick: ChallengeDetailViewEvent()
    object OnStarsClick: ChallengeDetailViewEvent()
    object OnBackPressed: ChallengeDetailViewEvent()
}