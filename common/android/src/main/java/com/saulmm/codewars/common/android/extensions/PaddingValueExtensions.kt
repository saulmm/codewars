package com.saulmm.codewars.common.android.extensions

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

fun PaddingValues.drawBehindNavigationValues(): PaddingValues {
    return PaddingValues(
        top = this.calculateTopPadding(),
        end = this.calculateRightPadding(LayoutDirection.Ltr),
        start = this.calculateRightPadding(LayoutDirection.Ltr),
        bottom = 0.dp,
    )
}