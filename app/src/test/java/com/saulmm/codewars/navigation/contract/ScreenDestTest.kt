package com.saulmm.codewars.navigation.contract

import androidx.navigation.NavType
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class ScreenDestTest {

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test route generation without arguments`() {
        val screenDest = ScreenDest("exampleRoute")

        assertThat(screenDest.route).isEqualTo("exampleRoute")
    }

    @Test
    fun `test route generation with arguments`() {
        val screenArg1 = ScreenArg("arg1", NavType.StringType, false)
        val screenArg2 = ScreenArg("arg2", NavType.IntType, true)

        val screenDest = ScreenDest("exampleRoute", listOf(screenArg1, screenArg2))

        assertThat(screenDest.route).isEqualTo("exampleRoute?arg1={arg1}&arg2={arg2}")
    }

    @Test
    fun `test navigation arguments generation`() {
        val screenArg = ScreenArg("arg", NavType.StringType, false)

        val screenDest = ScreenDest("exampleRoute", listOf(screenArg))

        val namedNavArgs = screenDest.navArgs
        assertThat(namedNavArgs).hasSize(1)
        assertThat(namedNavArgs[0].name).isEqualTo("arg")
    }

    @Test
    fun `test argsPath generation without arguments`() {
        val screenDest = ScreenDest("exampleRoute")

        assertThat(screenDest.argsPath).isEmpty()
    }
}
