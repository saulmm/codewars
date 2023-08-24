package com.saulmm.codewars

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.saulmm.codewars.feature.home.AuthoredChallengesViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
    fun authoredChallengesScreenViewModelFactory(): AuthoredChallengesViewModel.Factory
}

@Composable
fun authoredChallengesViewModel(userName: String): AuthoredChallengesViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).authoredChallengesScreenViewModelFactory()

    return viewModel(
        factory = AuthoredChallengesViewModel.provideFactory(
            assistedFactory = factory, userName = userName
        )
    )
}