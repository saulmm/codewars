package com.saulmm.codewars.navigation

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.saulmm.codewars.authoredChallengesViewModel
import com.saulmm.codewars.challengesDetailViewModel
import com.saulmm.codewars.feature.challenges.ui.authored.AuthoredChallengesScreen
import com.saulmm.common.navigation_contract.home.HomeGraphDest
import com.saulmm.codewars.feature.challenges.ui.detail.ChallengeDetailScreen
import com.saulmm.codewars.feature.challenges.ui.preferences.ui.PreferencesScreen
import com.saulmm.common.navigation_contract.home.SettingsGraphDest

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
                navigateToKataDetail = {
                    navController.navigate(HomeGraphDest.KataDetail.buildRoute(it))
                },
                navigateToSettings = {
                     navController.navigate(SettingsGraphDest.Settings.route)
                },
                viewModel = viewModel
            )
        }
        composable(
            route = HomeGraphDest.KataDetail.route,
            arguments = HomeGraphDest.KataDetail.navArgs
        ) {
            val context = LocalContext.current
            val challengeId = HomeGraphDest.KataDetail.kataIdFrom(it.arguments)
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
            route = SettingsGraphDest.Settings.route
        ) {
            PreferencesScreen()
        }
    }
}

