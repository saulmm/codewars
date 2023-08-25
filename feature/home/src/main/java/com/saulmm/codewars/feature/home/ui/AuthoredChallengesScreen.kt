@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.saulmm.codewars.feature.home.ui

import android.content.res.Configuration
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.saulmm.codewars.common.design.system.CodewarsTheme
import com.saulmm.codewars.common.design.system.component.CodewarsBackground
import com.saulmm.codewars.entity.Challenge
import com.saulmm.codewars.entity.ProgrammingLanguage
import com.saulmm.codewars.entity.Rank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun AuthoredChallengesScreen(
    userName: String,
    navigateToKataDetail: (String) -> Unit,
    viewModel: AuthoredChallengesViewModel,
    modifier: Modifier = Modifier,
) {
    initEventProcessor(navigateToKataDetail, viewModel)

    CodewarsTheme {
        CodewarsBackground {
            ChallengesScreenContent(viewModel)
        }
    }
}

@Composable
private fun ChallengesScreenContent(viewModel: AuthoredChallengesViewModel) {
    val viewState: AuthoredChallengesViewState by viewModel.viewState.collectAsStateWithLifecycle()
    when (viewState) {
        AuthoredChallengesViewState.Idle -> {

        }

        AuthoredChallengesViewState.Failure -> {
            ChallengesFailure()
        }

        is AuthoredChallengesViewState.Loaded -> {
            ChallengesLoaded(
                challenges = (viewState as AuthoredChallengesViewState.Loaded).katas,
                onChallengeClick = {
                    viewModel.onViewEvent(
                        AuthoredChallengesViewEvent.OnChallengeClick(
                            it
                        )
                    )
                }
            )
        }

        AuthoredChallengesViewState.Loading -> {
            ChallengesLoading()
        }
    }
}

@Composable
private fun initEventProcessor(
    navigateToKataDetail: (String) -> Unit,
    viewModel: AuthoredChallengesViewModel,
) {

    viewModel.events.observeWithLifecycle { event ->
        when (event) {
            is AuthoredChallengeEvent.NavigateToKataDetail -> {
                navigateToKataDetail(event.kataId)
            }
        }
    }

}

@Composable
fun ChallengesFailure() {
    Surface(
        color = Color.Red,
        modifier = Modifier.fillMaxSize()
    ) {
    }
}

@Composable
fun ChallengesLoading() {
    Surface(
        color = Color.Cyan,
        modifier = Modifier.fillMaxSize()
    ) {
    }
}

@Composable
fun ChallengesLoaded(
    challenges: List<Challenge>,
    onChallengeClick: (String) -> Unit
) {
    ChallengesList(challenges, onChallengeClick)
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

@Preview(
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ChallengeListPreviewDark() {
    CodewarsTheme {
        CodewarsBackground {
            ChallengesList(challenges = listOf(
                Challenge(
                    id = "",
                    name = "Walter's miraculous FizzBuzz factory",
                    description = "Walter's miraculous FizzBuzz factory",
                    rank = Rank.DAN_2,
                    tags = listOf("Arrays", "Algorithms", "Stacks", "Heaps"),
                    languages = listOf(ProgrammingLanguage.C, ProgrammingLanguage.KOTLIN)
                )
            ), onChallengeClick = {})
        }
    }
}

@Preview(
    showSystemUi = false,
)
@Composable
fun ChallengeListPreviewLight() {
    CodewarsTheme {
        CodewarsBackground {
            ChallengesList(challenges = listOf(
                Challenge(
                    id = "",
                    name = "Walter's miraculous FizzBuzz factory",
                    description = "Walter's miraculous FizzBuzz factory",
                    rank = Rank.DAN_2,
                    tags = listOf("Arrays", "Algorithms", "Stacks", "Heaps"),
                    languages = listOf(ProgrammingLanguage.C, ProgrammingLanguage.KOTLIN)
                )
            ), onChallengeClick = {})
        }
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ChallengeCard(
    challenge: Challenge,
    onChallengeClick: (String) -> Unit
) {
    Card(
        onClick = { onChallengeClick.invoke(challenge.id) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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
            ProgrammingLanguageTag(
                onClick = { },
                text = { Text(text = it.displayName) },
            )
        }
    }
}


@Composable
fun ProgrammingLanguageTag(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: @Composable () -> Unit,
) {
    Box(modifier = modifier) {
        TextButton(
            onClick = onClick,
            colors = ButtonDefaults.textButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = contentColorFor(backgroundColor = MaterialTheme.colorScheme.primaryContainer),
            ),
        ) {
            ProvideTextStyle(value = MaterialTheme.typography.labelSmall) {
                text()
            }
        }
    }
}




// TODO extract into a common android module
@Composable
inline fun <reified T> Flow<T>.observeWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    noinline action: suspend (T) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        lifecycleOwner.lifecycleScope.launch {
            flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).collect(action)
        }
    }
}