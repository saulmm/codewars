package com.saulmm.codewars.feature.challenges.ui.preferences.ui

sealed class PreferencesViewEvent {
    data class OnUsernameSelected(val newUsername: String): PreferencesViewEvent()
    object OnNavigateBackClick: PreferencesViewEvent()
}