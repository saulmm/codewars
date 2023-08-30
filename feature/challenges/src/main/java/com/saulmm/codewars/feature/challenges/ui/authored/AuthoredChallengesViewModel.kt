package com.saulmm.codewars.feature.challenges.ui.authored

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.saulmm.codewars.entity.Challenge
import com.saulmm.feature.challenges.model.params.ChallengePreviewParams
import com.saulmm.codewars.repository.Repository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class AuthoredChallengesViewModel @AssistedInject constructor(
    @Assisted private val userName: String,
    private val repository: Repository<ChallengePreviewParams, List<Challenge>>
) : ViewModel() {
    private val _viewState: MutableStateFlow<AuthoredChallengesViewState> =
        MutableStateFlow(AuthoredChallengesViewState.Idle)

    private val _events: Channel<AuthoredChallengeEvent> =
        Channel(Channel.BUFFERED)

    val viewState = _viewState.asStateFlow()
    val events = _events.receiveAsFlow()

    init {
        viewModelScope.launch {
            loadChallenges()
        }
    }

    suspend fun loadChallenges() {
        _viewState.value = AuthoredChallengesViewState.Loading

        runCatching {
            requireNotNull(
                repository.get(ChallengePreviewParams(userName))
            )
        }.onFailure {
            Timber.e(it)
            _viewState.value = AuthoredChallengesViewState.Failure
        }
        .onSuccess { _viewState.value = AuthoredChallengesViewState.Loaded(it) }
    }

    fun onViewEvent(viewEvent: AuthoredChallengesViewEvent) {
        when (viewEvent) {
            is AuthoredChallengesViewEvent.OnChallengeClick -> {
                _events.trySend(AuthoredChallengeEvent.NavigateToKataDetail(viewEvent.kataId))
            }

            AuthoredChallengesViewEvent.OnFailureTryAgainClick -> {
                viewModelScope.launch {
                    loadChallenges()
                }
            }
        }
    }


    @AssistedFactory
    interface Factory {
        fun create(userName: String): AuthoredChallengesViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            userName: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(userName) as T
            }

        }
    }
}