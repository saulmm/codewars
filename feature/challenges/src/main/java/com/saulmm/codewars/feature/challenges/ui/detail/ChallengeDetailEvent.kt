package com.saulmm.codewars.feature.challenges.ui.detail

import java.net.URI

sealed class ChallengeDetailEvent {
    object ShowScoreInfo: ChallengeDetailEvent()
    object ShowStarsInfo: ChallengeDetailEvent()
    object NavigateBack: ChallengeDetailEvent()
    data class NavigateToChallengeUrl(val uri: URI): ChallengeDetailEvent()
}
