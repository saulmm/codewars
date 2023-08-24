package com.saulmm.codewars.feature.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.saulmm.codewars.entity.Kata
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun AuthoredChallengesScreen(
    userName: String,
    navigateToKataDetail: (String) -> Unit,
    viewModel: AuthoredChallengesViewModel,
    modifier: Modifier = Modifier,
) {
    val viewState: AuthoredChallengesViewState by viewModel.viewState.collectAsStateWithLifecycle()
    initEventProcessor(navigateToKataDetail, viewModel)

    when (viewState) {
        AuthoredChallengesViewState.Idle -> {

        }

        AuthoredChallengesViewState.Failure -> {
            ChallengesFailure()
        }

        is AuthoredChallengesViewState.Loaded -> {
            ChallengesLoaded(
                challenges = (viewState as AuthoredChallengesViewState.Loaded).katas,
                onChallengeClick = { viewModel.onViewEvent(AuthoredChallengesViewEvent.OnChallengeClick(it))}
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
    challenges: List<Kata>,
    onChallengeClick: (String) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(challenges) { challenge ->
            Text(
                text = challenge.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .clickable {
                        onChallengeClick(challenge.id)
                    }
            )
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