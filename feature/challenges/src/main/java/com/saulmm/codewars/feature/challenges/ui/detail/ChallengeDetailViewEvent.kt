package com.saulmm.codewars.feature.challenges.ui.detail

import java.net.URI

sealed class ChallengeDetailViewEvent {
    data class OnUrlChipClick(val uri: URI): ChallengeDetailViewEvent()
    object OnScoreClick: ChallengeDetailViewEvent()
    object OnStarsClick: ChallengeDetailViewEvent()
    object OnBackPressed: ChallengeDetailViewEvent()
    object OnTryAgainClick: ChallengeDetailViewEvent()
}