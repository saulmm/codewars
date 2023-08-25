@file:OptIn(ExperimentalCoroutinesApi::class)

package com.saulmm.codewars.feature.home

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.saulmm.codewars.feature.home.model.AuthoredChallengesRepository
import com.saulmm.codewars.feature.home.ui.AuthoredChallengesViewModel
import com.saulmm.codewars.feature.home.ui.AuthoredChallengesViewState.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
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
    lateinit var authoredChallengesRepository: AuthoredChallengesRepository

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `when the viewmodel inits and repository returns, last emitted item is Loaded`() = runTest {
        `when`(authoredChallengesRepository.getFrom("")).thenReturn(emptyList())

        viewModel().viewState.test {
            awaitItem()
            assertThat(awaitItem()).isInstanceOf(Loading::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when the viewmodel inits, a Loaded event is the last emitted`() = runTest {
        `when`(authoredChallengesRepository.getFrom("")).thenReturn(emptyList())

        viewModel().viewState.test {
            awaitItem()
            awaitItem()
            assertThat(awaitItem()).isInstanceOf(Loaded::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun viewModel(): AuthoredChallengesViewModel {
        return AuthoredChallengesViewModel(
            userName = "",
            authoredChallengesRepository = authoredChallengesRepository
        )
    }
}