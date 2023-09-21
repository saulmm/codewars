package com.saulmm.codewars.feature.challenges.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.saulmm.codewars.entity.Challenge
import com.saulmm.codewars.repository.Repository
import com.saulmm.feature.challenges.model.params.ChallengePreviewParams
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchChallengeViewModel @AssistedInject constructor(
    @Assisted private val username: String,
    private val repository: Repository<ChallengePreviewParams, List<Challenge>>,
): ViewModel() {
    private val _events = Channel<SearchChallengesEvent>(Channel.BUFFERED)
    private val _viewState = MutableStateFlow<SearchChallengesViewState>(SearchChallengesViewState.Idle)
    val events = _events.receiveAsFlow()
    val viewState = _viewState.asStateFlow()

    fun onViewEvent(viewEvent: SearchChallengesViewEvent) {
        when (viewEvent) {
            SearchChallengesViewEvent.OnBackPressed -> {
                _events.trySend(SearchChallengesEvent.NavigateBack)
            }
            is SearchChallengesViewEvent.OnChallengeClick -> {
                _events.trySend(SearchChallengesEvent.NavigateToChallengeDetail(viewEvent.id))
            }
            is SearchChallengesViewEvent.OnTextQueryChanged -> {
                searchChallenges(viewEvent.query)
            }
            SearchChallengesViewEvent.OnFailureTryAgainClick -> {
                TODO()
            }
        }
    }

    private fun searchChallenges(query: String) {
        val searchQuery = ChallengePreviewParams.ByUsernameAndTextQuery(
            username = username,
            textQuery = query
        )
        viewModelScope.launch {
            _viewState.value = SearchChallengesViewState.Loading

            runCatching { repository.require(searchQuery) }
                .onFailure {
                    _viewState.value = SearchChallengesViewState.Failure
                    Timber.e(it)
                }
                .onSuccess {
                    _viewState.value = SearchChallengesViewState.Loaded(
                        challenges = it,
                        query = query
                    )
                }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(username: String): SearchChallengeViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            username: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(username = username) as T
            }
        }
    }
}