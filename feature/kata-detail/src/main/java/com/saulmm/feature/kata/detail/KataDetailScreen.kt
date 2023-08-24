package com.saulmm.feature.kata.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.saulmm.codewars.common.design.system.CodewarsTheme

@Composable
fun KataDetailScreen(
    kataId: String,
    modifier: Modifier = Modifier
) {
    CodewarsTheme {
        Text(
            text = "Kata ID for $kataId",
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxSize(),
        )
    }
}