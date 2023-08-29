package com.saulmm.codewars.common.extensions

import java.net.URI

fun String.toURIOrNull(): URI? {
    return try {
        URI.create(this)
    } catch (ignored: IllegalArgumentException) {
        null
    }
}