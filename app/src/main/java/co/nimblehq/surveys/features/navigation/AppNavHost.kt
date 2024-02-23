package co.nimblehq.surveys.features.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.nimblehq.surveys.features.forgot.password.ForgotPasswordScreen
import co.nimblehq.surveys.features.home.HomeScreen
import co.nimblehq.surveys.features.login.LoginScreen
import co.nimblehq.surveys.features.splash.SplashScreen
import co.nimblehq.surveys.features.survey.detail.SurveyDetailScreen

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppDestination.Splash.destination
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(destination = AppDestination.Splash) {
            SplashScreen(navController = navController)
        }

        composable(destination = AppDestination.Login) {
            LoginScreen(navController = navController)
        }

        composable(destination = AppDestination.ForgotPassword) {
            ForgotPasswordScreen(navController = navController)
        }

        composable(destination = AppDestination.Home) {
            HomeScreen(navController = navController)
        }

        composable(destination = AppDestination.SurveyDetail) { backStackEntry ->
            SurveyDetailScreen(
                id = AppDestination.SurveyDetail.getId(backStackEntry),
                navController = navController,
            )
        }
    }
}

private fun NavGraphBuilder.composable(
    destination: AppDestination,
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = destination.route,
        arguments = destination.arguments,
        deepLinks = deepLinks,
        content = content
    )
}

/**
 * Navigate to provided [AppDestination] with a Pair of key value String and Data [parcel]
 * Caution to use this method. This method use savedStateHandle to store the Parcelable data.
 * When previousBackstackEntry is popped out from navigation stack, savedStateHandle will return null and cannot retrieve data.
 * eg.Login -> Home, the Login screen will be popped from the back-stack on logging in successfully.
 */
fun NavHostController.navigate(
    destination: AppDestination,
    builder: NavOptionsBuilder.() -> Unit = {},
) {
    when (destination) {
        is AppDestination.Up -> navigateUp()
        else -> {
            destination.parcelableArgument.let { (key, value) ->
                currentBackStackEntry?.savedStateHandle?.set(key, value)
            }
            navigate(
                route = destination.destination,
                builder = builder,
            )
        }
    }
}
