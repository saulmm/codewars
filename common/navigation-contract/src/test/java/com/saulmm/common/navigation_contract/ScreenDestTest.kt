package com.saulmm.common.navigation_contract

import androidx.navigation.NavType
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ScreenDestTest {

    @Test
    fun `when creating route without args, then route is correct`() {
        val screenDest = ScreenDest("screen")
        assertEquals("screen", screenDest.route)
    }

    @Test
    fun `when creating route with args, then route contains args placeholders`() {
        val arg1 = ScreenArg("arg1", NavType.StringType, false)
        val arg2 = ScreenArg("arg2", NavType.IntType, true)
        val screenDest = ScreenDest("screen", listOf(arg1, arg2))
        assertEquals("screen?arg1={arg1}&arg2={arg2}", screenDest.route)
    }

    @Test
    fun `when generating args path without args, then argsPath is empty`() {
        val screenDest = ScreenDest("screen")
        assertEquals("", screenDest.argsPath)
    }

    @Test
    fun `when generating args path with args, then argsPath contains arg placeholders`() {
        val arg1 = ScreenArg("arg1", NavType.StringType, false)
        val arg2 = ScreenArg("arg2", NavType.IntType, true)
        val screenDest = ScreenDest("screen", listOf(arg1, arg2))
        assertEquals("?arg1={arg1}&arg2={arg2}", screenDest.argsPath)
    }
}
