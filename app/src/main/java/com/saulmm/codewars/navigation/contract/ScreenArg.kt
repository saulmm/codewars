package com.saulmm.codewars.navigation.contract

import androidx.navigation.NavType

data class ScreenArg<T>(
    val argName: String,
    val argType: NavType<T>,
    val isNullable: Boolean = false
)

