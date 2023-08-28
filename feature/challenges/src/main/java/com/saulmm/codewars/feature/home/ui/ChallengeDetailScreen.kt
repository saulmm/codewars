package com.saulmm.codewars.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saulmm.codewars.common.design.system.CodewarsTheme
import com.saulmm.codewars.common.design.system.component.CodewarsBackground
import com.saulmm.codewars.entity.ChallengeDetail
import com.saulmm.codewars.entity.Rank

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeDetailScreen(
    challengeId: String,
    viewModel: ChallengeDetailViewModel,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    CodewarsTheme {
        CodewarsBackground {
            val viewState: ChallengeDetailViewState by viewModel.viewState.collectAsStateWithLifecycle()
            val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

            Scaffold(
                modifier = Modifier
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    ChallengeDetailTopBar(
                        scrollBehavior = scrollBehavior,
                        onBackPressed = onBackPressed,
                    )
                }
            ) { padding ->
                when (viewState) {
                    ChallengeDetailViewState.Idle -> {}
                    ChallengeDetailViewState.Failure -> {
                        CharacterDetailFailure()
                    }

                    is ChallengeDetailViewState.Loaded -> {
                        ChallengeDetailContent(
                            challenge = (viewState as ChallengeDetailViewState.Loaded).challenge,
                            modifier = Modifier
                                .padding(padding)
                                .verticalScroll(rememberScrollState())
                        )
                    }

                    ChallengeDetailViewState.Loading -> {
                        CharacterDetailLoading()
                    }
                }

            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ChallengeDetailTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onBackPressed: () -> Unit,
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = Modifier
    )
}

@Composable
private fun CharacterDetailFailure() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Red)
    )
}

@Composable
private fun CharacterDetailLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue)
    )
}

@Composable
private fun ChallengeDetailContent(
    challenge: ChallengeDetail,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ChallengeDetailHeader(challenge = challenge)
        }
    }
}

@Composable
private fun ColumnScope.ChallengeDetailHeader(challenge: ChallengeDetail) {
    ChallengeDetailTitle(name = challenge.name)
    ChallengeDetailLabel(tags = challenge.tags)
    ChallengeDetailDescription(description = challenge.description)

}

@Composable
private fun ColumnScope.ChallengeDetailTitle(name: String) {
    Text(
        text = name,
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.displayMedium
    )
}

@Composable
private fun ColumnScope.ChallengeDetailLabel(tags: List<String>) {
    Text(
        text = tags.joinToString(separator = " • "),
        style = MaterialTheme.typography.labelLarge,
    )
}

@Composable
private fun ColumnScope.ChallengeDetailDescription(description: String) {
    Text(
        text = description,
        style = MaterialTheme.typography.bodyLarge,
    )
}

@Preview
@Composable
private fun ChallengeDetailContentPreview() {
    val detail = ChallengeDetail(
        id = "verear",
        name = "Josefa Ramos",
        description = "sit",
        rank = Rank.DAN_2,
        tags = listOf("Arrays", "Stacks", "Queues", "Trees"),
        languages = listOf(),
        url = null,
        stars = 7106,
        voteScore = 3559

    )

    CodewarsTheme {
        CodewarsBackground {
            ChallengeDetailContent(detail)
        }
    }
}
