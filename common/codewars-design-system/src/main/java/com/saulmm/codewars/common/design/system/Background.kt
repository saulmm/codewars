package com.saulmm.codewars.common.design.system

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Immutable
internal data class BackgroundTheme(
    val color: Color = Color.Unspecified,
    val tonalElevation: Dp = Dp.Unspecified,
)

internal val LocalBackgroundTheme = staticCompositionLocalOf { BackgroundTheme() }
