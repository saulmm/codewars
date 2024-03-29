package com.saulmm.common.navigation_contract.home

import android.os.Bundle
import androidx.navigation.NavType
import com.saulmm.common.navigation_contract.ScreenArg
import com.saulmm.common.navigation_contract.ScreenDest

sealed class HomeGraphDest {
    object AuthoredChallenges: ScreenDest("$HOME_GRAPH/home")


    object SearchChallenges: ScreenDest("$HOME_GRAPH/search") {
        private const val ARG_USERNAME = "username"

        override val args: List<ScreenArg<*>> = listOf(
            ScreenArg(ARG_USERNAME, NavType.StringType)
        )

        fun buildRoute(username: String): String {
            return route.replace("{$ARG_USERNAME}", username)
        }

        fun usernameFrom(bundle: Bundle?): String {
            return checkNotNull(bundle?.getString(ARG_USERNAME))
        }
    }


    object ChallengeDetail: ScreenDest("$HOME_GRAPH/kata-detail") {
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
