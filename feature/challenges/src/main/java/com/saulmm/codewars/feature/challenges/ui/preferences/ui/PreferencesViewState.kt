package com.saulmm.codewars.feature.challenges.ui.preferences.ui

sealed class PreferencesViewState {
    object Idle: PreferencesViewState()
    data class Loaded(val savedUsername: String): PreferencesViewState()
}