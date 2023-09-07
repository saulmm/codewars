@file:Suppress("PrivatePropertyName")

package com.saulmm.codewars.common.design.system

import android.app.Activity
import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

private val LightAndroidBackgroundTheme = BackgroundTheme(color = DarkGreenGray95)

private val DarkAndroidBackgroundTheme = BackgroundTheme(color = Color.Black)

@Composable
fun CodewarsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        IS_DYNAMIC_COLOR_SUPPORTED -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val backgroundTheme = when {
        IS_DYNAMIC_COLOR_SUPPORTED -> BackgroundTheme(color = colorScheme.primary)
        darkTheme -> DarkAndroidBackgroundTheme
        else -> LightAndroidBackgroundTheme
    }

    CompositionLocalProvider(
        LocalBackgroundTheme provides backgroundTheme,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = CodewarsTypography,
            content = content,
        )
    }
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
private val IS_DYNAMIC_COLOR_SUPPORTED = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S