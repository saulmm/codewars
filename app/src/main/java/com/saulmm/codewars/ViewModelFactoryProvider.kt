package com.saulmm.codewars

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.saulmm.codewars.feature.challenges.ui.detail.ChallengeDetailViewModel
import com.saulmm.codewars.feature.challenges.ui.search.SearchChallengeViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
    fun challengesDetailScreenViewModelFactory(): ChallengeDetailViewModel.Factory
    fun challengesSearchScreenViewModelFractory(): SearchChallengeViewModel.Factory

}

@Composable
fun viewModelFactory(): ViewModelFactoryProvider {
    return EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    )
}

@Composable
fun searchChallengesViewModel(username: String): SearchChallengeViewModel {
    val factory = viewModelFactory().challengesSearchScreenViewModelFractory()

    return viewModel(
        factory = SearchChallengeViewModel.provideFactory(
            assistedFactory = factory,
            username = username
        )
    )
}

@Composable
fun challengesDetailViewModel(challengeId: String): ChallengeDetailViewModel {
    val factory = viewModelFactory().challengesDetailScreenViewModelFactory()

    return viewModel(
        factory = ChallengeDetailViewModel.provideFactory(
            assistedFactory = factory, challengeId = challengeId
        )
    )
}