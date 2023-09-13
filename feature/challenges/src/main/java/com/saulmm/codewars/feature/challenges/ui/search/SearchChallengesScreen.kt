package com.saulmm.codewars.feature.challenges.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import com.saulmm.codewars.common.android.observeWithLifecycle
import com.saulmm.codewars.common.design.system.CodewarsTheme
import com.saulmm.codewars.common.design.system.component.CodewarsBackground


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
            Scaffold(
                topBar = { TopbarWithQueryInput(
                    onBackPressed = {
                        viewModel.onViewEvent(SearchChallengesViewEvent.OnBackPressed)
                    },
                    onQueryChange = {
                        viewModel.onViewEvent(SearchChallengesViewEvent.OnTextQueryChanged(it))
                    }
                ) }
            ) { paddingValues ->
                ChallengeResultsContent(paddingValues)
            }
        }
    }
}

@Composable
fun ChallengeResultsContent(paddingValues: PaddingValues) {
    TODO("Not yet implemented")
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

