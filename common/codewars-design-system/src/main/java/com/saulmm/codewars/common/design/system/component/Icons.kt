package com.saulmm.codewars.common.design.system.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun OnBackIconButton(onBackPressed: () -> Unit) {
    IconButton(onClick = onBackPressed
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back",
        )
    }
}