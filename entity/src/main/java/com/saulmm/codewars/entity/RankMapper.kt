package com.saulmm.codewars.entity

fun Rank.fromCodeOrUnknown(code: String) {
    Rank.values().find { it.code == code } ?: Rank.UKNOWN
}