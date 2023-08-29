@file:OptIn(ExperimentalLayoutApi::class, ExperimentalLayoutApi::class,
    ExperimentalLayoutApi::class
)

package com.saulmm.codewars.feature.home.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AssistChip
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.RichTextStyle
import com.halilibo.richtext.ui.material3.Material3RichText
import com.halilibo.richtext.ui.resolveDefaults
import com.saulmm.codewars.common.android.observeWithLifecycle
import com.saulmm.codewars.common.design.system.CodewarsTheme
import com.saulmm.codewars.common.design.system.component.CodewarsBackground
import com.saulmm.codewars.common.design.system.component.ProgrammingLanguageTag
import com.saulmm.codewars.entity.ChallengeDetail
import com.saulmm.codewars.entity.ProgrammingLanguage
import com.saulmm.codewars.entity.Rank
import com.saulmm.codewars.feature.home.R
import com.saulmm.codewars.feature.home.model.ChallengesRepository
import java.net.URI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeDetailScreen(
    viewModel: ChallengeDetailViewModel,
    navigateBack: () -> Unit,
    navigateToUrl: (URI) -> Unit,
) {
    CodewarsTheme {
        CodewarsBackground {
            val viewState: ChallengeDetailViewState by viewModel.viewState.collectAsStateWithLifecycle()
            val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

            initEventProcessor(
                navigateBack = navigateBack,
                navigateToUrl = navigateToUrl,
                viewModel = viewModel
            )

            Scaffold(
                modifier = Modifier
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    ChallengeDetailTopBar(
                        scrollBehavior = scrollBehavior,
                        onBackPressed = {
                            viewModel.onViewEvent(ChallengeDetailViewEvent.OnBackPressed)
                        }
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
                            onStarsClick = { viewModel.onViewEvent(ChallengeDetailViewEvent.OnStarsClick) },
                            onScoreClick = { viewModel.onViewEvent(ChallengeDetailViewEvent.OnScoreClick) },
                            onCodewarsClick = { viewModel.onViewEvent(ChallengeDetailViewEvent.OnUrlChipClick(it)) },
                            modifier = Modifier
                                .padding(padding)
                                .verticalScroll(rememberScrollState()),
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
    onStarsClick: () -> Unit,
    onScoreClick: () -> Unit,
    onCodewarsClick: (URI) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ChallengeDetailHeader(
                challenge = challenge,
                onStarsClick = onStarsClick,
                onScoreClick = onScoreClick,
                onCodewarsClick = onCodewarsClick,
            )
            ProgrammingLanguagesLayout(list = challenge.languages)
            ChallengeDetailDescription(description = challenge.description)

        }
    }
}

@ExperimentalLayoutApi
@Composable
private fun ChallengeDetailHeader(
    challenge: ChallengeDetail,
    onStarsClick: () -> Unit,
    onScoreClick: () -> Unit,
    onCodewarsClick: (URI) -> Unit,
) {
    ChallengeDetailTitle(name = challenge.name)
    ChallengeDetailLabel(tags = challenge.tags)
    ChallengeInfoRow(
        challenge = challenge,
        onStarsClick = onStarsClick,
        onScoreClick = onScoreClick,
        onCodewarsClick = onCodewarsClick
    )
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
private fun ChallengeInfoRow(
    challenge: ChallengeDetail,
    onStarsClick: () -> Unit,
    onScoreClick: () -> Unit,
    onCodewarsClick: (URI) -> Unit,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ChallengeStars(
            stars = challenge.stars,
            onClick = onStarsClick
        )

        ChallengeScore(
            voteScore = challenge.voteScore,
            onClick = onScoreClick
        )

        challenge.url?.let {
            UrlShortcut(
                url = it,
                onCodewarsClick = onCodewarsClick
            )
        }
    }
}

@Composable
private fun ChallengeStars(stars: Int, onClick: () -> Unit) {
    AssistChip(
        onClick = { onClick.invoke() },
        label = { Text(text = stars.toString()) },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = "$stars stars",
            )
        }
    )
}

@Composable
private fun ChallengeScore(voteScore: Int, onClick: () -> Unit) {
    AssistChip(
        onClick = { onClick.invoke() },
        label = { Text(text = "${voteScore}/100") },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_graph),
                contentDescription = "$voteScore",
            )
        }
    )
}

@Composable
private fun UrlShortcut(
    url: URI,
    onCodewarsClick: (URI) -> Unit,
) {
    AssistChip(
        onClick = { onCodewarsClick.invoke(url) },
        label = {
            Text(
                text = "Codewars",
                style = TextStyle(
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold
                ),
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_exit),
                contentDescription = "see on codewars",
            )
        }
    )
}



@Composable
private fun ChallengeDetailDescription(description: String) {
    var richTextStyle by remember { mutableStateOf(RichTextStyle().resolveDefaults()) }
    val codeBlockStyle = checkNotNull(richTextStyle.codeBlockStyle)
    val markDownBackgroundColor = MaterialTheme.colorScheme.tertiaryContainer
    val markDownForegroundColor = MaterialTheme.colorScheme.onTertiaryContainer

    LaunchedEffect(description) {
        richTextStyle = richTextStyle.copy(
            codeBlockStyle = codeBlockStyle.copy(
                textStyle = checkNotNull(codeBlockStyle.textStyle).copy(markDownForegroundColor),
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(markDownBackgroundColor)
            )
        )
    }
    Material3RichText(style = richTextStyle) { Markdown(content = description) }
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
            ProgrammingLanguage.KOTLIN, ProgrammingLanguage.C, ProgrammingLanguage.CLOJURE, ProgrammingLanguage.JAVA, ProgrammingLanguage.JAVASCRIPT,
            ProgrammingLanguage.COBOL, ProgrammingLanguage.COMMONLISP, ProgrammingLanguage.DART, ProgrammingLanguage.SCALA,
        ),
        url = URI.create("http://www.codewars.com"),
        stars = 7106,
        voteScore = 90

    )

    CodewarsTheme {
        CodewarsBackground {
            ChallengeDetailContent(
                challenge = detail,
                onStarsClick = {},
                onScoreClick = {},
                onCodewarsClick = {},
                modifier = Modifier,
            )
        }
    }
}

@Composable
private fun initEventProcessor(
    navigateBack: () -> Unit,
    navigateToUrl: (URI) -> Unit,
    viewModel: ChallengeDetailViewModel,
) {
    val context = LocalContext.current

    viewModel.events.observeWithLifecycle { event ->
        when (event) {
            ChallengeDetailEvent.NavigateBack -> {
                navigateBack.invoke()
            }
            is ChallengeDetailEvent.NavigateToChallengeUrl -> {
                navigateToUrl.invoke(event.uri)
            }
            ChallengeDetailEvent.ShowScoreInfo -> {
                Toast.makeText(context, context.getString(R.string.message_score), Toast.LENGTH_SHORT).show()
            }
            ChallengeDetailEvent.ShowStarsInfo -> {
                Toast.makeText(context, context.getString(R.string.message_stars), Toast.LENGTH_SHORT).show()
            }
        }
    }
}

