package com.saulmm.codewars.feature.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun HomeScreen(
    userName: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Home Screen for $userName",
        textAlign = TextAlign.Center,
        modifier = modifier.fillMaxSize(),
    )
}