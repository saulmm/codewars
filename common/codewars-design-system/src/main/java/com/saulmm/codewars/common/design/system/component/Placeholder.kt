package com.saulmm.codewars.common.design.system.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun Modifier.placeholder(): Modifier {
    return then(
        placeholder(
            visible = true,
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
            highlight = PlaceholderHighlight.shimmer(
                MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
            )
        )
    )
}