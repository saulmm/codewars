package com.saulmm.codewars.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import timber.log.Timber
import javax.inject.Named

class AuthoredChallengesViewModel @AssistedInject constructor(
    @Assisted private val userName: String,
): ViewModel() {

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