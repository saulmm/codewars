package com.saulmm.common.navigation_contract

import androidx.navigation.NavType

data class ScreenArg<T>(
    val argName: String,
    val argType: NavType<T>,
    val isNullable: Boolean = false
)

