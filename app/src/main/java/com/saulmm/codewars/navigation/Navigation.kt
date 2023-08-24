package com.saulmm.codewars.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.saulmm.codewars.feature.home.HomeScreen
import com.saulmm.common.navigation_contract.home.HomeGraphDest
import com.saulmm.feature.kata.detail.KataDetailScreen

@Composable
fun CodewarsNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = HomeGraphDest.AuthoredChallenges.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            route = HomeGraphDest.AuthoredChallenges.route,
            arguments = HomeGraphDest.AuthoredChallenges.navArgs
        ) {
            val userName = HomeGraphDest.AuthoredChallenges.userNameFrom(it.arguments)
            HomeScreen(userName = userName, navigateToKataDetail = {
                navController.navigate(HomeGraphDest.KataDetail.buildRoute(it))
            })
        }
        composable(
            route = HomeGraphDest.KataDetail.route,
            arguments = HomeGraphDest.KataDetail.navArgs
        ) {
            val kataId = HomeGraphDest.KataDetail.kataIdFrom(it.arguments)
            KataDetailScreen(kataId = kataId)
        }
    }
}

private val START_DESTINATION_ROUTE =
    HomeGraphDest.AuthoredChallenges.buildRoute(
        username = "bkaes"
    )
