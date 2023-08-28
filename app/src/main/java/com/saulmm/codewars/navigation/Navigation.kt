package com.saulmm.codewars.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.saulmm.codewars.authoredChallengesViewModel
import com.saulmm.codewars.challengesDetailViewModel
import com.saulmm.codewars.feature.home.ui.AuthoredChallengesScreen
import com.saulmm.common.navigation_contract.home.HomeGraphDest
import com.saulmm.codewars.feature.home.ui.ChallengeDetailScreen

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
            val viewModel = authoredChallengesViewModel(userName = userName)

            AuthoredChallengesScreen(
                userName = userName,
                viewModel = viewModel,
                navigateToKataDetail = {
                    navController.navigate(HomeGraphDest.KataDetail.buildRoute(it))
                }
            )
        }
        composable(
            route = HomeGraphDest.KataDetail.route,
            arguments = HomeGraphDest.KataDetail.navArgs
        ) {
            val challengeId = HomeGraphDest.KataDetail.kataIdFrom(it.arguments)
            val viewModel = challengesDetailViewModel(challengeId = challengeId)
            ChallengeDetailScreen(
                challengeId = challengeId,
                viewModel = viewModel,
                onBackPressed = { navController.popBackStack() }
            )
        }
    }
}

