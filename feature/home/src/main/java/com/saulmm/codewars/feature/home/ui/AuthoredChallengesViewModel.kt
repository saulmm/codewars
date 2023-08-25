package com.saulmm.codewars.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.saulmm.codewars.feature.home.model.AuthoredChallengesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AuthoredChallengesViewModel @AssistedInject constructor(
    @Assisted private val userName: String,
    private val authoredChallengesRepository: AuthoredChallengesRepository
): ViewModel() {
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
            authoredChallengesRepository.getFrom(userName)
        }
            .onFailure {
                _viewState.value = AuthoredChallengesViewState.Failure
            }
            .onSuccess {
                _viewState.value = AuthoredChallengesViewState.Loaded(it)
            }
    }

    fun onViewEvent(viewEvent: AuthoredChallengesViewEvent) {
        when (viewEvent) {
            is AuthoredChallengesViewEvent.OnChallengeClick -> {
                _events.trySend(AuthoredChallengeEvent.NavigateToKataDetail(viewEvent.kataId))
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