package com.saulmm.codewars

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.saulmm.codewars.feature.home.ui.AuthoredChallengesViewModel
import com.saulmm.codewars.feature.home.ui.ChallengeDetailViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
    fun authoredChallengesScreenViewModelFactory(): AuthoredChallengesViewModel.Factory
    fun challengesDetailScreenViewModelFactory(): ChallengeDetailViewModel.Factory
}

@Composable
fun viewModelFactory(): ViewModelFactoryProvider {
    return EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    )
}

@Composable
fun authoredChallengesViewModel(userName: String): AuthoredChallengesViewModel {
    val factory = viewModelFactory().authoredChallengesScreenViewModelFactory()

    return viewModel(
        factory = AuthoredChallengesViewModel.provideFactory(
            assistedFactory = factory, userName = userName
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