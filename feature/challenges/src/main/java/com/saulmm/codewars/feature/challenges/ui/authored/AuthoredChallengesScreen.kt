@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.saulmm.codewars.feature.challenges.ui.authored

import AuthoredChallengesViewState
import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saulmm.codewars.common.android.observeWithLifecycle
import com.saulmm.codewars.common.design.system.CodewarsTheme
import com.saulmm.codewars.common.design.system.LocalBackgroundTheme
import com.saulmm.codewars.common.design.system.component.CodewarsBackground
import com.saulmm.codewars.common.design.system.component.ErrorMessageWithAction
import com.saulmm.codewars.common.design.system.component.ProgrammingLanguageTag
import com.saulmm.codewars.common.design.system.component.TextFieldDialog
import com.saulmm.codewars.common.design.system.component.placeholder
import com.saulmm.codewars.entity.Challenge
import com.saulmm.codewars.entity.ProgrammingLanguage
import com.saulmm.codewars.feature.challenges.R

@Composable
fun AuthoredChallengesScreen(
    navigateToKataDetail: (String) -> Unit,
    navigateToSettings: () -> Unit,
    viewModel: AuthoredChallengesViewModel,
) {
    var showSearchDialog: Boolean by remember {
        mutableStateOf(false)
    }

    val navigateToSearch = {
        showSearchDialog = true
    }

    initEventProcessor(
        navigateToKataDetail = navigateToKataDetail,
        navigateToSettings = navigateToSettings,
        navigateToSearch = navigateToSearch,
        viewModel = viewModel
    )

    CodewarsTheme {
        CodewarsBackground {
            ChallengesScreenContent(viewModel)
            SearchChallengeDialog(
                show = showSearchDialog,
                onChallengeQuerySelected = {
                    showSearchDialog = false
                    viewModel.onViewEvent(AuthoredChallengesViewEvent.OnSearchQuerySelected(it))
                }
            )
        }
    }
}

@Composable
private fun AuthoredChallengesHeader(userName: String) {
    Column(Modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.title_home, userName),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = stringResource(id = R.string.message_home_welcome, userName),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 24.dp),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun ChallengesScreenContent(viewModel: AuthoredChallengesViewModel) {
    val viewState: AuthoredChallengesViewState by viewModel.viewState.collectAsStateWithLifecycle()

    val onFailureTryAgainClick = {
        viewModel.onViewEvent(AuthoredChallengesViewEvent.OnFailureTryAgainClick)
    }

    val onChallengeClick = { challengeId: String ->
        viewModel.onViewEvent(AuthoredChallengesViewEvent.OnChallengeClick(challengeId))
    }

    val onSettingsClick = {
        viewModel.onViewEvent(AuthoredChallengesViewEvent.OnSettingsClick)
    }
    
    val onSearchClick = {
        viewModel.onViewEvent(AuthoredChallengesViewEvent.OnSearchClick)
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = LocalBackgroundTheme.current.color,
        topBar = {
            AuthoredChallengesTopBar(
                scrollBehavior = scrollBehavior,
                onSettingsClick = onSettingsClick,
                onSearchClick = onSearchClick,
            )
        }
    ) { paddingValues ->
        Log.i("view-stage", "View state is: $viewState")
        AnimatedContent(
            targetState = viewState,
            transitionSpec = { fadeIn(tween(300)) togetherWith fadeOut(tween(300)) },
            label = "Animated Content"
        ) { targetState ->
            when (viewState) {
                AuthoredChallengesViewState.Idle -> {}
                is AuthoredChallengesViewState.Failure -> {
                    Log.i("view-stage", "Hello, I'm failure branch")
                    ChallengesFailure(
                        paddingValues = paddingValues,
                        userName = targetState.username,
                        onTryAgainClick = onFailureTryAgainClick,
                    )
                }
                is AuthoredChallengesViewState.Loaded -> {
                    Log.i("view-stage", "Hello, I'm Loaded branch")
                    ChallengesLoaded(
                        paddingValues = paddingValues,
                        userName = targetState.username,
                        challenges = (viewState as AuthoredChallengesViewState.Loaded).katas,
                        onChallengeClick = onChallengeClick
                    )
                }
                is AuthoredChallengesViewState.Loading -> {
                    Log.i("view-stage", "Hello, I'm Loading branch")
                    ChallengesLoading(
                        paddingValues = paddingValues,
                        userName = targetState.username
                    )
                }
                else -> {
                    error("Cannot happen")
                }
            }
        }
    }
}

@Composable
private fun initEventProcessor(
    navigateToKataDetail: (String) -> Unit,
    navigateToSettings: () -> Unit,
    navigateToSearch: () -> Unit,
    viewModel: AuthoredChallengesViewModel,
) {

    viewModel.events.observeWithLifecycle { event ->
        when (event) {
            is AuthoredChallengeEvent.NavigateToKataDetail -> {
                navigateToKataDetail(event.kataId)
            }

            AuthoredChallengeEvent.NavigateToSettings ->
                navigateToSettings()

            AuthoredChallengeEvent.NavigateToSearch ->
                navigateToSearch()
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AuthoredChallengesTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onSettingsClick: () -> Unit,
    onSearchClick: () -> Unit,
) {
    TopAppBar(
        title = {},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = LocalBackgroundTheme.current.color,
            scrolledContainerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        actions = {
            IconButton(onClick = { onSearchClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.primary
                )

            }
            IconButton(onClick = { onSettingsClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = "Settings",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = Modifier
    )
}

@Composable
fun ChallengesFailure(
    userName: String,
    onTryAgainClick: () -> Unit,
    paddingValues: PaddingValues = PaddingValues()
) {
    Column(modifier = Modifier.padding(paddingValues)) {
        AuthoredChallengesHeader(userName = userName)
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
fun ChallengesLoading(userName: String, paddingValues: PaddingValues) {
    Box(Modifier.padding(paddingValues)) {
        Column(modifier = Modifier.padding(16.dp)) {
            AuthoredChallengesHeader(userName = userName)
            Spacer(modifier = Modifier.height(16.dp))
                repeat(2) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .height(196.dp)
                            .placeholder()
                    ) {}
                    Spacer(modifier = Modifier.height(24.dp))
                }

        }
    }

}

@Composable
fun ChallengesLoaded(
    userName: String,
    challenges: List<Challenge>,
    onChallengeClick: (String) -> Unit,
    paddingValues: PaddingValues
) {
    Box(modifier = Modifier.padding(paddingValues)) {
        ChallengesList(userName, challenges, onChallengeClick)
    }
}

@Composable
private fun ChallengesList(
    userName: String,
    challenges: List<Challenge>,
    onChallengeClick: (String) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            AuthoredChallengesHeader(userName)
        }
        items(challenges, key = { it.id }) { challenge ->
            ChallengeCard(
                challenge = challenge,
                onChallengeClick = onChallengeClick
            )
        }
    }
}

@Preview
@Composable
private fun ChallengesFailurePreview() {
    CodewarsTheme {
        CodewarsBackground {
            ChallengesFailure(
                userName = "Otis Hahn",
                {},
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ChallengeListPreviewDark() {
    CodewarsTheme {
        CodewarsBackground {
            ChallengesList(
                userName = "Yep!",
                onChallengeClick = {},
                challenges = listOf(
                    Challenge(
                        id = "1",
                        name = "Walter's miraculous FizzBuzz factory",
                        description = "Walter's miraculous FizzBuzz factory",
                        tags = listOf("Arrays", "Algorithms", "Stacks", "Heaps"),
                        languages = listOf(ProgrammingLanguage.C, ProgrammingLanguage.KOTLIN)
                    ),
                    Challenge(
                        id = "2",
                        name = "Walter's miraculous FizzBuzz factory",
                        description = "Walter's miraculous FizzBuzz factory",
                        tags = listOf("Arrays", "Algorithms", "Stacks", "Heaps"),
                        languages = listOf(ProgrammingLanguage.C, ProgrammingLanguage.KOTLIN)
                    )
                ),

            )
        }
    }
}

@Preview(showBackground = true,)
@Composable
private fun ChallengeListPreviewLight() {
    CodewarsTheme {
        CodewarsBackground {
            ChallengesList(
                userName = "Yep!",
                onChallengeClick = {},
                challenges = listOf(
                    Challenge(
                        id = "1",
                        name = "Walter's miraculous FizzBuzz factory",
                        description = "Walter's miraculous FizzBuzz factory",
                        tags = listOf("Arrays", "Algorithms", "Stacks", "Heaps"),
                        languages = listOf(ProgrammingLanguage.C, ProgrammingLanguage.KOTLIN)
                    ),
                    Challenge(
                        id = "2",
                        name = "Walter's miraculous FizzBuzz factory",
                        description = "Walter's miraculous FizzBuzz factory",
                        tags = listOf("Arrays", "Algorithms", "Stacks", "Heaps"),
                        languages = listOf(ProgrammingLanguage.C, ProgrammingLanguage.KOTLIN)
                    )
                )
            )
        }
    }
}

@Composable
private fun ChallengeCard(
    challenge: Challenge,
    onChallengeClick: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.clickable { onChallengeClick.invoke(challenge.id) }
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            Text(
                text = challenge.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = challenge.tags.joinToString(separator = " • "),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = challenge.description,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 4,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            ProgramingLanguages(progammingLanguages = challenge.languages)
        }
    }
}

@Composable
fun SearchChallengeDialog(
    show: Boolean,
    onChallengeQuerySelected: (String) -> Unit,
) {
    TextFieldDialog(
        show = show,
        title = stringResource(id = R.string.title_search_challenge),
        onPositiveButtonClicked = onChallengeQuerySelected
    )

}

@Composable
fun ProgramingLanguages(
    progammingLanguages: List<ProgrammingLanguage>,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        items(progammingLanguages) {
            ProgrammingLanguageTag(programmingLanguage = it)
        }
    }
}