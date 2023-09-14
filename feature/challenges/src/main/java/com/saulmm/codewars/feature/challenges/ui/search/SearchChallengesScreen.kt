package com.saulmm.codewars.feature.challenges.ui.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saulmm.codewars.common.android.observeWithLifecycle
import com.saulmm.codewars.common.design.system.CodewarsTheme
import com.saulmm.codewars.common.design.system.component.ChallengeCard
import com.saulmm.codewars.common.design.system.component.ChallengesLoadingPlaceholders
import com.saulmm.codewars.common.design.system.component.CodewarsBackground
import com.saulmm.codewars.common.design.system.component.ErrorMessageWithAction
import com.saulmm.codewars.entity.Challenge
import com.saulmm.codewars.feature.challenges.R


@ExperimentalMaterial3Api
@Composable
fun SearchChallengesScreen(
    navigateToChallengeDetail: (String) -> Unit,
    navigateBack: () -> Unit,
    viewModel : SearchChallengeViewModel
) {
    initEventProcessor(
        navigateBack = navigateBack,
        navigateToChallengeId = navigateToChallengeDetail,
        viewModel = viewModel
    )

    CodewarsTheme {
        CodewarsBackground {
            val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

            Scaffold(
                topBar = {
                    TopbarWithQueryInput(
                        onBackPressed = {
                            viewModel.onViewEvent(SearchChallengesViewEvent.OnBackPressed)
                        },
                        onQueryChange = {
                            viewModel.onViewEvent(SearchChallengesViewEvent.OnTextQueryChanged(it))
                        }
                    )
                },
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
            ) { paddingValues ->
                ChallengeResultsContent(paddingValues, viewModel)
            }
        }
    }
}

@Composable
fun ChallengeResultsContent(
    paddingValues: PaddingValues,
    viewModel: SearchChallengeViewModel,
) {
    val viewState: SearchChallengesViewState by viewModel.viewState.collectAsStateWithLifecycle()

    val onFailureTryAgainClick = {
        viewModel.onViewEvent(SearchChallengesViewEvent.OnFailureTryAgainClick)
    }

    val onChallengeClick = { challengeId: String ->
        viewModel.onViewEvent(SearchChallengesViewEvent.OnChallengeClick(challengeId))
    }

    AnimatedContent(
        targetState = viewState,
        transitionSpec = { fadeIn(tween(300)) togetherWith fadeOut(tween(300)) },
        label = "Animated Content"
    ) {targetState ->
        when (targetState) {
            SearchChallengesViewState.Failure -> {
                SearchChallengesFailure(
                    onTryAgainClick = onFailureTryAgainClick,
                    modifier = Modifier.padding(paddingValues)
                )
            }
            SearchChallengesViewState.Idle -> {
                SearchChallengesIdle(modifier = Modifier.padding(paddingValues))
            }
            is SearchChallengesViewState.Loaded -> {
                ChallengesList(
                    challenges = (viewState as SearchChallengesViewState.Loaded).challenges,
                    onChallengeClick = onChallengeClick
                )
            }

            SearchChallengesViewState.Loading -> {
                ChallengesLoadingPlaceholders(modifier = Modifier.padding(16.dp))
            }
        }
    }
}


@Composable
fun SearchChallengesIdle(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
    ) {
        Text(
            text = stringResource(id = R.string.title_idle_search),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = stringResource(id = R.string.message_idle_search),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.bodyLarge,
        )
    }

}

@Composable
fun SearchChallengesFailure(
    onTryAgainClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(32.dp))
        ErrorMessageWithAction(
            titleStringRes = R.string.message_error_challenges_title,
            messageStringRes = R.string.message_error_challenges,
            actionStringRes = R.string.action_try_again,
            onTryAgainClick = onTryAgainClick
        )
    }
}


@Composable
private fun ChallengesList(
    challenges: List<Challenge>,
    onChallengeClick: (String) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(challenges, key = { it.id }) { challenge ->
            ChallengeCard(
                challenge = challenge,
                onChallengeClick = onChallengeClick
            )
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun TopbarWithQueryInput(
    onBackPressed: () -> Unit,
    onQueryChange: (String) -> Unit
) {
    var challengeQueryText by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    TopAppBar(title = {
        BasicTextField(
            value = challengeQueryText,
            onValueChange = { newValue ->
                challengeQueryText = newValue
                onQueryChange(newValue)
            },
            decorationBox = { innerTextField ->
                Box {
                    if (challengeQueryText.isEmpty()) {
                        Text(
                            text = "Arrays, stacks, Fizzbuzz...",
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = .6f),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    innerTextField()
                }
            },
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.focusRequester(focusRequester)
        )
    })
}

@Composable
private fun initEventProcessor(
    navigateBack: () -> Unit,
    navigateToChallengeId: (String) -> Unit,
    viewModel: SearchChallengeViewModel
) {

    viewModel.events.observeWithLifecycle { event ->
        when (event) {
            SearchChallengesEvent.NavigateBack -> {
                navigateBack()
            }
            is SearchChallengesEvent.NavigateToChallengeDetail -> {
                navigateToChallengeId(event.challengeId)
            }
        }
    }
}

@Composable
@Preview
private fun SearchChallengesIdlePreview() {
    CodewarsTheme {
        CodewarsBackground {
            SearchChallengesIdle()
        }
    }
}

