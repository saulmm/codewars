@file:OptIn(ExperimentalCoroutinesApi::class)

package com.saulmm.codewars.feature.challenges.ui.authored

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.saulmm.codewars.entity.Challenge
import com.saulmm.codewars.feature.challenges.MainDispatcherRule
import com.saulmm.feature.challenges.model.params.ChallengePreviewParams
import com.saulmm.codewars.feature.challenges.ui.authored.AuthoredChallengesViewState.*
import com.saulmm.codewars.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.saulmm.common.android.repository.PreferencesRepository


@ExperimentalCoroutinesApi
class AuthoredChallengesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var challengesRepository: Repository<ChallengePreviewParams, List<Challenge>>

    @Mock
    lateinit var preferencesRepository: PreferencesRepository

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `when the viewmodel inits and repository returns, last emitted item is Loaded`() = runTest {
        `when`(challengesRepository.get(ChallengePreviewParams(""))).thenReturn(emptyList())

        viewModel().viewState.test {
            awaitItem() // Idle
            assertThat(awaitItem()).isInstanceOf(Loading::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when the viewmodel inits, a Loaded event is the last emitted`() = runTest {
        `when`(challengesRepository.get(ChallengePreviewParams(""))).thenReturn(emptyList())

        Dispatchers.setMain(UnconfinedTestDispatcher())

        viewModel().viewState.test {
            assertThat(expectMostRecentItem()).isInstanceOf(Loaded::class.java)
        }

        Dispatchers.resetMain()
    }

    @Test
    fun `when clicking on a challenge, a NavigateToDetail event should be emitted`() = runTest {
        `when`(challengesRepository.get(ChallengePreviewParams(""))).thenReturn(emptyList())

        val viewModel = viewModel().also {
            it.onViewEvent(AuthoredChallengesViewEvent.OnChallengeClick(""))
        }

        viewModel.events.test {
            assertThat(awaitItem()).isInstanceOf(AuthoredChallengeEvent.NavigateToKataDetail::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when loading challenges fail, most recent view state should be failure`() = runTest {
        `when`(challengesRepository.get(ChallengePreviewParams(""))).thenThrow(RuntimeException())

        Dispatchers.setMain(UnconfinedTestDispatcher())

        viewModel().viewState.test {
            assertThat(expectMostRecentItem()).isInstanceOf(Failure::class.java)
        }

        Dispatchers.resetMain()
    }

    @Test
    fun `when clicking on try again to restart the request, a second loading is emitted`() = runTest {
        `when`(challengesRepository.get(ChallengePreviewParams(""))).thenThrow(RuntimeException())

        val viewModel = viewModel()

        viewModel.viewState.test {
            awaitItem() // IDLE
            assertThat(awaitItem()).isInstanceOf(Loading::class.java)
            assertThat(awaitItem()).isInstanceOf(Failure::class.java)

            viewModel.onViewEvent(AuthoredChallengesViewEvent.OnFailureTryAgainClick)

            assertThat(awaitItem()).isInstanceOf(Loading::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun viewModel(): AuthoredChallengesViewModel {
        mock<PreferencesRepository>() {
            onBlocking { preferencesRepository.getString(PreferencesRepository.Key.SELECTED_USERNAME) }
                .thenReturn("")
        }

        return AuthoredChallengesViewModel(
            preferencesRepository = preferencesRepository,
            repository = challengesRepository
        )
    }
}