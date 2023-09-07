@file:OptIn(ExperimentalMaterial3Api::class)

package com.saulmm.codewars.feature.challenges.ui.preferences.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saulmm.codewars.common.android.observeWithLifecycle
import com.saulmm.codewars.common.design.system.CodewarsTheme
import com.saulmm.codewars.common.design.system.component.CodewarsBackground
import com.saulmm.codewars.common.design.system.component.OnBackIconButton
import com.saulmm.codewars.feature.challenges.R

@Composable
fun PreferencesScreen(
    viewModel: PreferencesViewModel,
    onNavigateBack: () -> Unit = {},
    onNavigateToAuthoredChallenges: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val viewState: PreferencesViewState by viewModel.viewState.collectAsStateWithLifecycle()
    val onUsernameSelected = { username: String ->
        viewModel.onViewEvent(PreferencesViewEvent.OnUsernameSelected(username))
    }


    initEventProcessor(
        navigateBack = onNavigateBack,
        navigateToAuthoredChallenges = onNavigateToAuthoredChallenges,
        viewModel = viewModel
    )


    CodewarsTheme {
        CodewarsBackground {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    SettingsTopBar(
                        scrollBehavior = scrollBehavior,
                        onBackPressed = onNavigateBack,
                    )
                }
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    when (viewState) {
                        PreferencesViewState.Idle -> {
                            // no op
                        }

                        is PreferencesViewState.Loaded -> {
                            PreferencesContent(
                                username = (viewState as PreferencesViewState.Loaded).savedUsername,
                                onUsernameSelected = onUsernameSelected
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PreferencesContent(
    username: String,
    onUsernameSelected: (username: String) -> Unit
) {
    var openDialog by remember { mutableStateOf(false) }
    val onConfirmClicked = { openDialog = true }

    PreferencesContentLoaded(
        username = username,
        onChangeUserClick = onConfirmClicked
    )
    ChangeUserNameDialog(
        username = username,
        openDialog = openDialog,
        onDialogDismissed = { openDialog = false },
        onConfirmClicked = {
            openDialog = false
            onUsernameSelected(it)
        }
    )

}

@Composable
private fun PreferencesContentLoaded(
    username: String,
    onChangeUserClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(space = 24.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.label_preferences),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(start = 72.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onChangeUserClick() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_user_2),
                contentDescription = "user",
                modifier = Modifier.padding(16.dp)

            )
            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = stringResource(id = R.string.label_selected_user),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(id = R.string.message_selected_user, username),
                    modifier = Modifier.alpha(0.7f),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun SettingsTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onBackPressed: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.action_settings),
                modifier = Modifier.padding(start = 24.dp)
            )
        },
        scrollBehavior = scrollBehavior,
        navigationIcon = { OnBackIconButton(onBackPressed = onBackPressed) }
    )

}


@Composable
fun ChangeUserNameDialog(
    username: String,
    openDialog: Boolean,
    onDialogDismissed: () -> Unit,
    onConfirmClicked: (String) -> Unit,
) {
    if (openDialog) {
        var text by remember { mutableStateOf(username) }

        AlertDialog(
            onDismissRequest = onDialogDismissed,
            content = {
                Surface(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(),
                    shape = MaterialTheme.shapes.large,
                    tonalElevation = AlertDialogDefaults.TonalElevation
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = stringResource(id = R.string.title_write_user),
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = stringResource(id = R.string.message_write_user, username),
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.alpha(0.7f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = text,
                            onValueChange = { text = it },
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row {
                            TextButton(onClick = { onDialogDismissed() }) {
                                Text(text = stringResource(id = R.string.action_cancel))
                            }
                            TextButton(onClick = { onConfirmClicked(text) }) {
                                Text(text = stringResource(id = R.string.action_confirm))
                            }
                        }

                    }
                }
            }
        )
    }
}

@Composable
private fun initEventProcessor(
    navigateBack: () -> Unit,
    navigateToAuthoredChallenges: () -> Unit,
    viewModel: PreferencesViewModel,
) {
    viewModel.events.observeWithLifecycle { event ->
        when (event) {
            PreferencesEvent.NavigateBack -> {
                navigateBack()
            }
            PreferencesEvent.NavigateToAuthoredChallenges -> {
                navigateToAuthoredChallenges()
            }
        }

    }
}