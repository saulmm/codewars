package com.saulmm.codewars.navigation

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import com.saulmm.codewars.common.design.system.animation.CodewarsEnterTransition
import com.saulmm.codewars.common.design.system.animation.CodewarsExitTransition
import com.saulmm.codewars.feature.challenges.ui.authored.AuthoredChallengesScreen
import com.saulmm.codewars.feature.challenges.ui.authored.AuthoredChallengesViewModel
import com.saulmm.common.navigation_contract.home.HomeGraphDest
import com.saulmm.codewars.feature.challenges.ui.detail.ChallengeDetailScreen
import com.saulmm.codewars.feature.challenges.ui.preferences.ui.PreferencesScreen
import com.saulmm.codewars.feature.challenges.ui.preferences.ui.PreferencesViewModel
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




