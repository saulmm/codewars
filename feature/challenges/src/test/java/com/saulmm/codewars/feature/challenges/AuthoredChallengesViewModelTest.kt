@file:OptIn(ExperimentalCoroutinesApi::class)

package com.saulmm.codewars.feature.challenges

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.saulmm.codewars.feature.challenges.model.ChallengesRepository
import com.saulmm.codewars.feature.challenges.ui.authored.AuthoredChallengeEvent
import com.saulmm.codewars.feature.challenges.ui.authored.AuthoredChallengesViewEvent
import com.saulmm.codewars.feature.challenges.ui.authored.AuthoredChallengesViewModel
import com.saulmm.codewars.feature.challenges.ui.authored.AuthoredChallengesViewState.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class AuthoredChallengesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var challengesRepository: ChallengesRepository

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `when the viewmodel inits and repository returns, last emitted item is Loaded`() = runTest {
        `when`(challengesRepository.getFrom("")).thenReturn(emptyList())

        viewModel().viewState.test {
            awaitItem() // Idle
            assertThat(awaitItem()).isInstanceOf(Loading::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when the viewmodel inits, a Loaded event is the last emitted`() = runTest {
        `when`(challengesRepository.getFrom("")).thenReturn(emptyList())

        Dispatchers.setMain(UnconfinedTestDispatcher())

        viewModel().viewState.test {
            assertThat(expectMostRecentItem()).isInstanceOf(Loaded::class.java)
        }

        Dispatchers.resetMain()
    }

    @Test
    fun `when clicking on a challenge, a NavigateToDetail event should be emitted`() = runTest {
        `when`(challengesRepository.getFrom("")).thenReturn(emptyList())

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
        `when`(challengesRepository.getFrom("")).thenThrow(RuntimeException())

        Dispatchers.setMain(UnconfinedTestDispatcher())

        viewModel().viewState.test {
            assertThat(expectMostRecentItem()).isInstanceOf(Failure::class.java)
        }

        Dispatchers.resetMain()
    }

    @Test
    fun `when clicking on try again to restart the request, a second loading is emitted`() = runTest {
        `when`(challengesRepository.getFrom("")).thenThrow(RuntimeException())

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
        return AuthoredChallengesViewModel(
            userName = "",
            challengesRepository = challengesRepository
        )
    }
}