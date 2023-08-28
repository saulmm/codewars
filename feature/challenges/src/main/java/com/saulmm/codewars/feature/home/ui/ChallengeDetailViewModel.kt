package com.saulmm.codewars.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.saulmm.codewars.feature.home.model.ChallengesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ChallengeDetailViewModel @AssistedInject constructor(
    @Assisted private val challengeId: String,
    private val repository: ChallengesRepository,
): ViewModel() {

    private val _viewState = MutableStateFlow(ChallengeDetailViewState.Idle)

    init {
        viewModelScope.launch {
            loadCharacterDetail()
        }
    }

    private suspend fun loadCharacterDetail() {
        runCatching {
            repository.challengeDetail(challengeId)
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