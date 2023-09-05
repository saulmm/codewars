package com.saulmm.codewars.feature.challenges.ui.preferences.ui

sealed class PreferencesEvent {
    object NavigateToAuthoredChallenges: PreferencesEvent()
    object NavigateBack: PreferencesEvent()
}