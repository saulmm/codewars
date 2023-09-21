@file:OptIn(ExperimentalMaterial3Api::class)

package com.saulmm.codewars.common.design.system.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldDialog(
    show: Boolean,
    title: String,
    message: String? = null,
    positiveText: String? = null,
    negativeText: String? = null,
    onDialogDismissed: () -> Unit = {},
    onPositiveButtonClicked: (result: String) -> Unit,
    onNegativeButtonClicked: () -> Unit = { onDialogDismissed() },

) {
    if (show) {
        var text by remember { mutableStateOf("") }

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
                            text = title,
                            style = MaterialTheme.typography.titleLarge,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        message?.let {
                            Text(
                                text = message,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.alpha(0.7f)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = text,
                            onValueChange = { text = it },
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(horizontalArrangement = Arrangement.End) {
                            negativeText?.let {
                                TextButton(onClick = { onNegativeButtonClicked() }) {
                                    Text(text = it)
                                }
                            }
                            TextButton(onClick = { onPositiveButtonClicked(text) }) {
                                Text(text = positiveText ?: stringResource(id = android.R.string.ok))
                            }
                        }

                    }
                }
            }
        )
    }

}