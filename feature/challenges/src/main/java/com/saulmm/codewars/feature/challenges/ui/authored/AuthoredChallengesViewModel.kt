package com.saulmm.codewars.feature.challenges.ui.authored

import AuthoredChallengesViewState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saulmm.codewars.entity.Challenge
import com.saulmm.feature.challenges.model.params.ChallengePreviewParams
import com.saulmm.codewars.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.saulmm.common.android.repository.PreferencesRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthoredChallengesViewModel @Inject constructor(
    private val repository: Repository<ChallengePreviewParams, List<Challenge>>,
    private val preferencesRepository: PreferencesRepository,
) : ViewModel() {
    private val _viewState: MutableStateFlow<AuthoredChallengesViewState> =
        MutableStateFlow(AuthoredChallengesViewState.Idle)

    private val _events: Channel<AuthoredChallengeEvent> =
        Channel(Channel.BUFFERED)

    val viewState = _viewState.asStateFlow()
    val events = _events.receiveAsFlow()

    init {
        viewModelScope.launch {
            loadChallengesByUsername()
        }
    }

    suspend fun loadChallengesByUsername() {
        val username = preferencesRepository.getString(PreferencesRepository.Key.SELECTED_USERNAME)
        _viewState.value = AuthoredChallengesViewState.Loading(username)

        runCatching {
            requireNotNull(
                repository.get(ChallengePreviewParams.ByUsername(username))
            )
        }.onFailure {
            Timber.e(it)
            _viewState.value = AuthoredChallengesViewState.Failure(username)
        }
        .onSuccess {
            _viewState.value = AuthoredChallengesViewState.Loaded(
                username = username,
                katas = it
        ) }
    }

    fun onViewEvent(viewEvent: AuthoredChallengesViewEvent) {
        when (viewEvent) {
            is AuthoredChallengesViewEvent.OnChallengeClick -> {
                _events.trySend(AuthoredChallengeEvent.NavigateToKataDetail(viewEvent.kataId))
            }

            AuthoredChallengesViewEvent.OnFailureTryAgainClick -> {
                viewModelScope.launch {
                    loadChallengesByUsername()
                }
            }

            AuthoredChallengesViewEvent.OnSettingsClick -> {
                _events.trySend(AuthoredChallengeEvent.NavigateToSettings)
            }

            AuthoredChallengesViewEvent.OnSearchClick -> {
                viewModelScope.launch(Dispatchers.IO) { // Accessing to disk via prefs
                    val username = preferencesRepository.getString(PreferencesRepository.Key.SELECTED_USERNAME)
                    _events.trySend(AuthoredChallengeEvent.NavigateToSearch(username))
                }
            }
        }
    }
}