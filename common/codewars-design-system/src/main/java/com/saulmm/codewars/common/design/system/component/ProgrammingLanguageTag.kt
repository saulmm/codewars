package com.saulmm.codewars.common.design.system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.saulmm.codewars.entity.ProgrammingLanguage

@Composable
fun ProgrammingLanguageTag(
    modifier: Modifier = Modifier,
    programmingLanguage: ProgrammingLanguage,
) {
    Box(modifier = modifier
        .clip(RoundedCornerShape(8.dp))
        .background(MaterialTheme.colorScheme.secondaryContainer)
        .padding(all = 8.dp)
    ) {
        Text(
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            text = programmingLanguage.displayName
        )
    }
}
