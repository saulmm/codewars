package com.saulmm.codewars.navigation.home

import android.os.Bundle
import androidx.navigation.NavType
import com.saulmm.codewars.navigation.contract.ScreenArg
import com.saulmm.codewars.navigation.contract.ScreenDest

sealed class HomeGraphDest {
    object AuthoredChallenges: ScreenDest("$HOME_GRAPH/home") {
        private const val ARG_USER_NAME = "username"

        override val args: List<ScreenArg<*>> = listOf(
            ScreenArg(ARG_USER_NAME, NavType.StringType)
        )

        fun buildRoute(username: String): String {
            return route.replace("{$ARG_USER_NAME}", username)
        }

        fun userNameFrom(bundle: Bundle?): String {
            return checkNotNull(bundle?.getString(ARG_USER_NAME))
        }
    }

    object KataDetail: ScreenDest("$HOME_GRAPH/kata-detail") {
        private const val ARG_KATA_ID = "kata_id"

        override val args: List<ScreenArg<*>> = listOf(
            ScreenArg(ARG_KATA_ID, NavType.StringType)
        )

        fun buildRoute(kataId: String): String {
            return route.replace("{$ARG_KATA_ID}", kataId)
        }

        fun kataIdFrom(bundle: Bundle?): String {
            return checkNotNull(bundle?.getString(ARG_KATA_ID))
        }
    }


    companion object {
        private const val HOME_GRAPH = "home"
    }
}
