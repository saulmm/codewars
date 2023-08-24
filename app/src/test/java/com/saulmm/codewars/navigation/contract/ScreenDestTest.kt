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
        val screenDest = com.saulmm.common.navigation_contract.ScreenDest("exampleRoute")

        assertThat(screenDest.route).isEqualTo("exampleRoute")
    }

    @Test
    fun `test route generation with arguments`() {
        val screenArg1 =
            com.saulmm.common.navigation_contract.ScreenArg("arg1", NavType.StringType, false)
        val screenArg2 =
            com.saulmm.common.navigation_contract.ScreenArg("arg2", NavType.IntType, true)

        val screenDest = com.saulmm.common.navigation_contract.ScreenDest(
            "exampleRoute",
            listOf(screenArg1, screenArg2)
        )

        assertThat(screenDest.route).isEqualTo("exampleRoute?arg1={arg1}&arg2={arg2}")
    }

    @Test
    fun `test navigation arguments generation`() {
        val screenArg =
            com.saulmm.common.navigation_contract.ScreenArg("arg", NavType.StringType, false)

        val screenDest =
            com.saulmm.common.navigation_contract.ScreenDest("exampleRoute", listOf(screenArg))

        val namedNavArgs = screenDest.navArgs
        assertThat(namedNavArgs).hasSize(1)
        assertThat(namedNavArgs[0].name).isEqualTo("arg")
    }

    @Test
    fun `test argsPath generation without arguments`() {
        val screenDest = com.saulmm.common.navigation_contract.ScreenDest("exampleRoute")

        assertThat(screenDest.argsPath).isEmpty()
    }
}
