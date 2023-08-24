package com.saulmm.codewars.entity

fun rankValueOfOrUnknown(code: String): Rank {
    return Rank.values().find { it.code == code } ?: Rank.UKNOWN
}