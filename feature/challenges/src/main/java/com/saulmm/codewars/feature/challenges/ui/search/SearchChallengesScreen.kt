@file:OptIn(ExperimentalLayoutApi::class)

package com.saulmm.codewars.feature.challenges.ui.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saulmm.codewars.common.android.extensions.drawBehindNavigationValues
import com.saulmm.codewars.common.android.observeWithLifecycle
import com.saulmm.codewars.common.design.system.CodewarsTheme
import com.saulmm.codewars.common.design.system.component.ChallengesLoadingPlaceholders
import com.saulmm.codewars.common.design.system.component.CodewarsBackground
import com.saulmm.codewars.common.design.system.component.ErrorMessageWithAction
import com.saulmm.codewars.common.design.system.component.OnBackIconButton
import com.saulmm.codewars.entity.Challenge
import com.saulmm.codewars.feature.challenges.R


@ExperimentalMaterial3Api
@Composable
fun SearchChallengesScreen(
    navigateToChallengeDetail: (String) -> Unit,
    navigateBack: () -> Unit,
    viewModel: SearchChallengeViewModel
) {
    initEventProcessor(
        navigateBack = navigateBack,
        navigateToChallengeId = navigateToChallengeDetail,
        viewModel = viewModel
    )

    CodewarsTheme {
            CodewarsBackground {
                val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                val focusRequester = remember { FocusRequester() }

                Scaffold(
                    topBar = {
                        SearchToolbar(
                            scrollBehavior = scrollBehavior,
                            focusRequester = focusRequester,
                            onBackPressed = {
                                viewModel.onViewEvent(SearchChallengesViewEvent.OnBackPressed)
                            },
                            onQueryChange = {
                                viewModel.onViewEvent(SearchChallengesViewEvent.OnTextQueryChanged(it))
                            }
                        )
                    },
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
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

    val drawBehindNavigationPaddingValues = paddingValues.drawBehindNavigationValues()

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
    ) { targetState ->
        when (targetState) {
            SearchChallengesViewState.Failure -> {
                SearchChallengesFailure(
                    onTryAgainClick = onFailureTryAgainClick,
                    modifier = Modifier.padding(drawBehindNavigationPaddingValues)
                )
            }

            SearchChallengesViewState.Idle -> {
                SearchChallengesIdle(modifier = Modifier.padding(drawBehindNavigationPaddingValues))
            }

            is SearchChallengesViewState.Loaded -> {
                ChallengesList(
                    challenges = targetState.challenges,
                    onChallengeClick = onChallengeClick,
                    query = targetState.query,
                    modifier = Modifier.padding(drawBehindNavigationPaddingValues)
                )
            }

            SearchChallengesViewState.Loading -> {
                ChallengesLoadingPlaceholders(modifier = Modifier.padding(drawBehindNavigationPaddingValues))
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


@ExperimentalLayoutApi
@Composable
private fun ChallengesList(
    query: String,
    challenges: List<Challenge>,
    onChallengeClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    HideKeyboardAnRemoveFocusOnScroll(lazyListState)

    Box(modifier = modifier) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            state = lazyListState,
        ) {
            items(challenges, key = { it.id }) { challenge ->
                SearchResultAlternative(
                    currentQuery = query,
                    challenge = challenge,
                    onChallengeClick = onChallengeClick
                )
            }
        }
    }
}

@ExperimentalLayoutApi
@Composable
@OptIn(ExperimentalComposeUiApi::class)
private fun HideKeyboardAnRemoveFocusOnScroll(
    lazyListState: LazyListState
) {
    val isScrolling = lazyListState.isScrollInProgress
    val isImeVisible = WindowInsets.isImeVisible
    val keyboardController = LocalSoftwareKeyboardController.current
    var hasHiddenKeyboard by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(isImeVisible, isScrolling) {
        if (isImeVisible && isScrolling && !hasHiddenKeyboard) {
            keyboardController?.hide()
            hasHiddenKeyboard = true
            focusManager.clearFocus()
        } else if (!isScrolling) {
            hasHiddenKeyboard = false
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun SearchToolbar(
    scrollBehavior: TopAppBarScrollBehavior,
    focusRequester: FocusRequester,
    onBackPressed: () -> Unit,
    onQueryChange: (String) -> Unit
) {

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    TopAppBar(
        title = {
            SearchTextField(
                onQueryChange = onQueryChange,
                focusRequester = focusRequester
            )
        },
        scrollBehavior = scrollBehavior,
        navigationIcon = { OnBackIconButton(onBackPressed = onBackPressed) },
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun SearchTextField(
    onQueryChange: (String) -> Unit,
    focusRequester: FocusRequester
) {
    var challengeQueryText by remember { mutableStateOf("") }

    TextField(
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        value = challengeQueryText,
        onValueChange = { newValue ->
            challengeQueryText = newValue
            onQueryChange(newValue)
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    challengeQueryText = ""
                    onQueryChange("")
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(16.dp)
                )
            }.takeIf { challengeQueryText.isNotEmpty() }
        },
        textStyle = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .focusRequester(focusRequester)
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(32.dp),
        maxLines = 1,
        singleLine = true,
    )
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

@Preview(showBackground = true,)
@Composable
private fun SearchResultAlternativePreview() {
    SearchResultAlternative(
        currentQuery = "compose",
        challenge = Challenge(
            id = "b",
            name = "Material components in compose are composable",
            description = "Jetpack Compose offers an implementation of Material Design, a comprehensive design system for creating digital interfaces. You can use composable functions to implement Material components.",
            tags = listOf("Arrays", "Stacks", "Queues"),
            languages = listOf()
        ),
        onChallengeClick = {}
    )
}

@Composable
private fun SearchResultAlternative(
    currentQuery: String,
    challenge: Challenge,
    onChallengeClick: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 2.dp, horizontal = 16.dp)
            .clickable { onChallengeClick(challenge.id) }
    ) {

        Icon(
            painter = painterResource(id = R.drawable.ic_search), contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(12.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        val queryIndexesInResult = firstOcurrenceIndexes(result = challenge.name, query = currentQuery)
        val anotatedString = buildAnnotatedString {
            append(challenge.name)

            queryIndexesInResult?.let {
                addStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    ),
                    start = it.first,
                    end = it.second
                )
            }
        }

        Text(
            text = anotatedString,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

fun firstOcurrenceIndexes(result: String, query: String): Pair<Int, Int>? {
    val startIndex = result.lowercase().indexOf(query.lowercase())

    if (startIndex != -1) {
        val endIndex = startIndex + query.length
        return Pair(startIndex, endIndex)
    }

    return null
}

@Preview(
    showBackground = true,
)
@Composable
private fun SearchResultPreviewLoaded() {
    CodewarsTheme {
        CodewarsBackground {
            ChallengesList(
                query = "Compose",
                challenges = listOf(
                    Challenge(
                        id = "a",
                        name = "Material components in Compose",
                        description = "Jetpack Compose offers an implementation of Material Design, a comprehensive design system for creating digital interfaces. You can use composable functions to implement Material components.",
                        tags = listOf("Arrays", "Stacks", "Queues"),
                        languages = listOf()
                    ),
                    Challenge(
                        id = "b",
                        name = "Material components in Compose",
                        description = "Jetpack Compose offers an implementation of Material Design, a comprehensive design system for creating digital interfaces. You can use composable functions to implement Material components.",
                        tags = listOf("Arrays", "Stacks", "Queues"),
                        languages = listOf()
                    ),
                    Challenge(
                        id = "c",
                        name = "Material components in Compose",
                        description = "Jetpack Compose offers an implementation of Material Design, a comprehensive design system for creating digital interfaces. You can use composable functions to implement Material components.",
                        tags = listOf("Arrays", "Stacks", "Queues"),
                        languages = listOf()
                    ),
                    Challenge(
                        id = "d",
                        name = "Material components in Compose",
                        description = "Jetpack Compose offers an implementation of Material Design, a comprehensive design system for creating digital interfaces. You can use composable functions to implement Material components.",
                        tags = listOf("Arrays", "Stacks", "Queues"),
                        languages = listOf()
                    ),

                ),
                onChallengeClick = {},
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

