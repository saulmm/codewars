package com.saulmm.codewars.feature.challenges.ui.detail

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.google.common.truth.Truth.*
import com.saulmm.codewars.entity.ChallengeDetail
import com.saulmm.codewars.entity.Rank
import com.saulmm.codewars.feature.challenges.MainDispatcherRule
import com.saulmm.codewars.feature.challenges.model.ChallengesRepository
import com.saulmm.codewars.feature.challenges.model.params.ChallengeDetailParams
import com.saulmm.codewars.feature.challenges.ui.authored.AuthoredChallengeEvent
import com.saulmm.codewars.feature.challenges.ui.authored.AuthoredChallengesViewEvent
import com.saulmm.codewars.feature.challenges.ui.authored.AuthoredChallengesViewState
import com.saulmm.codewars.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.net.URI

@ExperimentalCoroutinesApi
class ChallengeDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var challengesRepository: Repository<ChallengeDetailParams, ChallengeDetail>

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `when the viewmodel inits and repository returns, last emitted item is Loaded`() = runTest {
        `when`(challengesRepository.get(ChallengeDetailParams(""))).thenReturn(CHALLENGE_DETAIL_FIXTURE)

        viewModel().viewState.test {
            awaitItem() // Idle
            assertThat(awaitItem()).isInstanceOf(ChallengeDetailViewState.Loading::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when the viewmodel inits, a Loaded event is the last emitted`() = runTest {
        `when`(challengesRepository.get(ChallengeDetailParams(""))).thenReturn(CHALLENGE_DETAIL_FIXTURE)

        Dispatchers.setMain(UnconfinedTestDispatcher())

        viewModel().viewState.test {
            assertThat(expectMostRecentItem()).isInstanceOf(ChallengeDetailViewState.Loaded::class.java)
        }

        Dispatchers.resetMain()
    }

    @Test
    fun `when clicking on the stars, a ShowStarsInfo event should be emitted`() = runTest {
        `when`(challengesRepository.get(ChallengeDetailParams(""))).thenReturn(CHALLENGE_DETAIL_FIXTURE)

        val viewModel = viewModel().also {
            it.onViewEvent(ChallengeDetailViewEvent.OnStarsClick)
        }

        viewModel.events.test {
            assertThat(awaitItem()).isInstanceOf(ChallengeDetailEvent.ShowStarsInfo::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when clicking on the score, a ShowScoreInfo event should be emitted`() = runTest {
        `when`(challengesRepository.get(ChallengeDetailParams(""))).thenReturn(CHALLENGE_DETAIL_FIXTURE)

        val viewModel = viewModel().also {
            it.onViewEvent(ChallengeDetailViewEvent.OnScoreClick)
        }

        viewModel.events.test {
            assertThat(awaitItem()).isInstanceOf(ChallengeDetailEvent.ShowScoreInfo::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when clicking on codewars, a NavigateToChallengeUrl event should be emitted`() = runTest {
        `when`(challengesRepository.get(ChallengeDetailParams(""))).thenReturn(CHALLENGE_DETAIL_FIXTURE)
        val urlFixture = URI.create("http://www.google.es")
        val viewModel = viewModel().also {
            it.onViewEvent(ChallengeDetailViewEvent.OnUrlChipClick(urlFixture))
        }

        viewModel.events.test {
            assertThat(awaitItem()).isEqualTo(ChallengeDetailEvent.NavigateToChallengeUrl(urlFixture))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when clicking back, a NavigateBack event should be emitted`() = runTest {
        `when`(challengesRepository.get(ChallengeDetailParams(""))).thenReturn(CHALLENGE_DETAIL_FIXTURE)
        val viewModel = viewModel().also {
            it.onViewEvent(ChallengeDetailViewEvent.OnBackPressed)
        }

        viewModel.events.test {
            assertThat(awaitItem()).isEqualTo(ChallengeDetailEvent.NavigateBack)
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun viewModel(): ChallengeDetailViewModel {
        return ChallengeDetailViewModel(
            challengeId = "",
            repository = challengesRepository
        )
    }

    private companion object {
        val CHALLENGE_DETAIL_FIXTURE = ChallengeDetail(
            id = "partiendo",
            name = "Corrine Molina",
            description = "lacus",
            rank = Rank.DAN_2,
            tags = listOf(),
            languages = listOf(),
            url = null,
            stars = 6760,
            voteScore = 8133
        )
    }
}