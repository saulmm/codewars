package com.saulmm.codewars.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class ChallengeDetailViewModel @AssistedInject constructor(
    @Assisted private val challengeId: String
) {

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