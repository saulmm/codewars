package com.saulmm.codewars.feature.challenges.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.saulmm.codewars.feature.challenges.model.ChallengesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ChallengeDetailViewModel @AssistedInject constructor(
    @Assisted private val challengeId: String,
    private val repository: ChallengesRepository,
): ViewModel() {

    private val _viewState: MutableStateFlow<ChallengeDetailViewState> =
        MutableStateFlow(ChallengeDetailViewState.Idle)

    private val _events = Channel<ChallengeDetailEvent>(Channel.BUFFERED)

    val events = _events.receiveAsFlow()
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch { loadCharacterDetail() }
    }

    private suspend fun loadCharacterDetail() {
        _viewState.value = ChallengeDetailViewState.Loading

        runCatching {
            delay(5_000)
            repository.challengeDetail(challengeId)
        }
            .onFailure { _viewState.value = ChallengeDetailViewState.Failure }
            .onSuccess { _viewState.value = ChallengeDetailViewState.Loaded(it) }
    }

    fun onViewEvent(event: ChallengeDetailViewEvent) {
        when (event) {
            ChallengeDetailViewEvent.OnScoreClick -> { _events.trySend(ChallengeDetailEvent.ShowScoreInfo) }
            ChallengeDetailViewEvent.OnStarsClick -> { _events.trySend(ChallengeDetailEvent.ShowStarsInfo) }
            is ChallengeDetailViewEvent.OnUrlChipClick -> { _events.trySend(
                ChallengeDetailEvent.NavigateToChallengeUrl(
                    event.uri
                )
            ) }
            ChallengeDetailViewEvent.OnBackPressed -> { _events.trySend(ChallengeDetailEvent.NavigateBack) }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(challengeId: String): ChallengeDetailViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            challengeId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(challengeId) as T
            }
        }
    }
}