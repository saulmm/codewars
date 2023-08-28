@file:OptIn(ExperimentalLayoutApi::class)

package com.saulmm.codewars.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import com.saulmm.codewars.common.design.system.component.ProgrammingLanguageTag
import com.saulmm.codewars.entity.ChallengeDetail
import com.saulmm.codewars.entity.ProgrammingLanguage
import com.saulmm.codewars.entity.ProgrammingLanguage.*
import com.saulmm.codewars.entity.Rank

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeDetailScreen(
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
@ExperimentalLayoutApi
@OptIn(ExperimentalLayoutApi::class)
private fun ProgrammingLanguagesLayout(list: List<ProgrammingLanguage>) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        list.forEach {
            ProgrammingLanguageTag(programmingLanguage = it)
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
private fun ChallengeDetailHeader(challenge: ChallengeDetail) {
    ChallengeDetailTitle(name = challenge.name)
    ChallengeDetailLabel(tags = challenge.tags)
    ChallengeDetailDescription(description = challenge.description)
    ProgrammingLanguagesLayout(list = challenge.languages)
}

@Composable
private fun ChallengeDetailTitle(name: String) {
    Text(
        text = name,
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.displayMedium
    )
}

@Composable
private fun ChallengeDetailLabel(tags: List<String>) {
    Text(
        text = tags.joinToString(separator = " • "),
        style = MaterialTheme.typography.labelLarge,
    )
}

@Composable
private fun ChallengeDetailDescription(description: String) {
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
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Nulla nec lorem id diam tristique pulvinar porta a ante. " +
                "Ut et iaculis lectus. Quisque gravida, nisi quis commodo vestibulum, " +
                "nibh risus lobortis elit, id eleifend neque orci eget nunc. Donec quis lacus odio. Ut volutpat leo sit amet nisi suscipit placerat. Praesent faucibus malesuada massa at condimentum. Morbi in lorem ut sapien accumsan scelerisque. Pellentesque nec convallis sapien. Aenean non congue lectus. Aliquam interdum euismod ante id euismod. Etiam auctor venenatis euismod. Integer risus quam, ullamcorper ultrices tortor id, maximus porttitor nisl.",
        rank = Rank.DAN_2,
        tags = listOf("Arrays", "Stacks", "Queues", "Trees"),
        languages = listOf(
            KOTLIN, C, CLOJURE, JAVA, JAVASCRIPT,
            KOTLIN, C, CLOJURE, JAVA, JAVASCRIPT,
            KOTLIN, C, CLOJURE, JAVA, JAVASCRIPT,
        ),
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
