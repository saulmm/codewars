package com.saulmm.codewars.feature.challenges.ui.preferences.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.saulmm.common.android.repository.PreferencesRepository
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val preferenceRepository: PreferencesRepository
): ViewModel() {

    private val _viewState: MutableStateFlow<PreferencesViewState> = MutableStateFlow(PreferencesViewState.Idle)
    private val _events: Channel<PreferencesEvent> = Channel(Channel.BUFFERED)
    val viewState: StateFlow<PreferencesViewState> = _viewState.asStateFlow()
    val events = _events.receiveAsFlow()

    init {
        viewModelScope.launch {
            loadPreferences()
        }
    }

    private suspend fun loadPreferences() {
        val userName = preferenceRepository.getString(PreferencesRepository.Key.SELECTED_USERNAME)
        _viewState.value = PreferencesViewState.Loaded(userName)
    }

    fun onViewEvent(event: PreferencesViewEvent) {
        when (event) {
            is PreferencesViewEvent.OnUsernameSelected -> {
                saveUsername(event.newUsername)
                _events.trySend(PreferencesEvent.NavigateToAuthoredChallenges)
            }

            PreferencesViewEvent.OnNavigateBackClick -> {
                _events.trySend(PreferencesEvent.NavigateBack)
            }
        }
    }

    private fun saveUsername(newUsername: String) {
        viewModelScope.launch {
            preferenceRepository.setString(PreferencesRepository.Key.SELECTED_USERNAME, newUsername)
        }
    }
}