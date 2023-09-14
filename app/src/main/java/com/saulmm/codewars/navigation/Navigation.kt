package com.saulmm.codewars.navigation

import android.content.Intent
import android.net.Uri
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.saulmm.codewars.challengesDetailViewModel
import com.saulmm.codewars.searchChallengesViewModel
import com.saulmm.codewars.common.design.system.animation.CodewarsEnterTransition
import com.saulmm.codewars.common.design.system.animation.CodewarsExitTransition
import com.saulmm.codewars.feature.challenges.ui.authored.AuthoredChallengesScreen
import com.saulmm.codewars.feature.challenges.ui.authored.AuthoredChallengesViewModel
import com.saulmm.common.navigation_contract.home.HomeGraphDest
import com.saulmm.codewars.feature.challenges.ui.detail.ChallengeDetailScreen
import com.saulmm.codewars.feature.challenges.ui.preferences.ui.PreferencesScreen
import com.saulmm.codewars.feature.challenges.ui.preferences.ui.PreferencesViewModel
import com.saulmm.codewars.feature.challenges.ui.search.SearchChallengesScreen
import com.saulmm.common.navigation_contract.home.SettingsGraphDest

@ExperimentalMaterial3Api
@Composable
fun CodewarsNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = HomeGraphDest.AuthoredChallenges.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
        enterTransition = { CodewarsEnterTransition },
        exitTransition = { CodewarsExitTransition }

    ) {
        composable(
            route = HomeGraphDest.AuthoredChallenges.route,
        ) {
            val viewModel: AuthoredChallengesViewModel = hiltViewModel()

            AuthoredChallengesScreen(
                navigateToKataDetail = {
                    navController.navigate(HomeGraphDest.ChallengeDetail.buildRoute(it))
                },
                navigateToSettings = {
                     navController.navigate(SettingsGraphDest.Settings.route)
                },
                viewModel = viewModel
            )
        }
        composable(
            route = HomeGraphDest.ChallengeDetail.route,
            arguments = HomeGraphDest.ChallengeDetail.navArgs
        ) {
            val context = LocalContext.current
            val challengeId = HomeGraphDest.ChallengeDetail.kataIdFrom(it.arguments)
            val viewModel = challengesDetailViewModel(challengeId = challengeId)
            ChallengeDetailScreen(
                viewModel = viewModel,
                navigateBack = { navController.popBackStack() },
                navigateToUrl = { url ->
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url.toString())))
                }
            )
        }
        composable(
            route = HomeGraphDest.ChallengeDetail.route,
            arguments = HomeGraphDest.ChallengeDetail.navArgs
        ) {
            val context = LocalContext.current
            val challengeId = HomeGraphDest.ChallengeDetail.kataIdFrom(it.arguments)
            val viewModel = challengesDetailViewModel(challengeId = challengeId)
            ChallengeDetailScreen(
                viewModel = viewModel,
                navigateBack = { navController.popBackStack() },
                navigateToUrl = { url ->
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url.toString())))
                }
            )
        }
        composable(
            route = HomeGraphDest.SearchChallenges.route,
            arguments = HomeGraphDest.SearchChallenges.navArgs
        ) {
            val username = HomeGraphDest.SearchChallenges.usernameFrom(it.arguments)
            val viewModel = searchChallengesViewModel(username = username)

            SearchChallengesScreen(
                navigateToChallengeDetail = {
                    navController.navigate(HomeGraphDest.ChallengeDetail.buildRoute(it))
                },
                navigateBack = { navController.popBackStack() },
                viewModel = viewModel
            )
        }

        composable(
            route = SettingsGraphDest.Settings.route
        ) {
            val viewModel: PreferencesViewModel = hiltViewModel()
            PreferencesScreen(
                viewModel = viewModel,
                onNavigateBack = {
                     navController.popBackStack()
                },
                onNavigateToAuthoredChallenges = {
                     navController.navigate(HomeGraphDest.AuthoredChallenges.route) {
                         navOptions {
                             popUpTo(route = HomeGraphDest.AuthoredChallenges.route) {
                                 inclusive = true
                             }
                         }
                     }
                },

            )
        }
    }
}




