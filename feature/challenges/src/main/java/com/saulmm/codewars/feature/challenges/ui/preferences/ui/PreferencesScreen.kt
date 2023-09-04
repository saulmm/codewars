@file:OptIn(ExperimentalMaterial3Api::class)

package com.saulmm.codewars.feature.challenges.ui.preferences.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saulmm.codewars.common.design.system.CodewarsTheme
import com.saulmm.codewars.common.design.system.component.CodewarsBackground
import com.saulmm.codewars.common.design.system.component.OnBackIconButton
import com.saulmm.codewars.feature.challenges.R

@Composable
fun PreferencesScreen(
    onBackPressed: () -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    CodewarsTheme {
        CodewarsBackground {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    SettingsTopBar(
                        scrollBehavior = scrollBehavior,
                        onBackPressed = onBackPressed,
                    )
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier.padding(paddingValues),

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
                            modifier = Modifier.padding(start = 72.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
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
                                    text = stringResource(id = R.string.message_selected_user),
                                    modifier = Modifier.alpha(0.7f),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
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
        title = { Text(
            text = stringResource(id = R.string.action_settings),
            modifier = Modifier.padding(start = 24.dp)
        ) },
        scrollBehavior = scrollBehavior,
        navigationIcon = { OnBackIconButton(onBackPressed = onBackPressed) }
    )
    
}

@Preview
@Composable
private fun PreferencesScreenPreview() {
    PreferencesScreen()
}
