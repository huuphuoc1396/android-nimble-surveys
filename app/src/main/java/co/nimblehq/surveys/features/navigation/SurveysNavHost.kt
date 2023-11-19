package co.nimblehq.surveys.features.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.nimblehq.surveys.features.forgot.password.ForgotPasswordScreen
import co.nimblehq.surveys.features.home.HomeScreen
import co.nimblehq.surveys.features.login.LoginScreen
import co.nimblehq.surveys.features.splash.SplashScreen
import co.nimblehq.surveys.features.survey.detail.SurveyDetailScreen

@Composable
fun SurveysNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = SurveysDestination.Splash.destination
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(destination = SurveysDestination.Splash) {
            SplashScreen(navigator = { destination ->
                navController.navigate(destination, destination.parcelableArgument)
            })
        }

        composable(destination = SurveysDestination.Login) {
            LoginScreen(navigator = { destination ->
                navController.navigate(destination, destination.parcelableArgument)
            })
        }

        composable(destination = SurveysDestination.ForgotPassword) {
            ForgotPasswordScreen(navigator = { destination ->
                navController.navigate(destination, destination.parcelableArgument)
            })
        }

        composable(destination = SurveysDestination.Home) {
            HomeScreen(navigator = { destination ->
                navController.navigate(destination, destination.parcelableArgument)
            })
        }

        composable(destination = SurveysDestination.SurveyDetail) { backStackEntry ->
            SurveyDetailScreen(
                navigator = { destination -> navController.navigate(destination) },
                id = backStackEntry.arguments?.getString(KEY_ID).orEmpty()
            )
        }
    }
}

private fun NavGraphBuilder.composable(
    destination: SurveysDestination,
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = destination.route,
        arguments = destination.arguments,
        deepLinks = deepLinks,
        content = content
    )
}

/**
 * Navigate to provided [SurveysDestination] with a Pair of key value String and Data [parcel]
 * Caution to use this method. This method use savedStateHandle to store the Parcelable data.
 * When previousBackstackEntry is popped out from navigation stack, savedStateHandle will return null and cannot retrieve data.
 * eg.Login -> Home, the Login screen will be popped from the back-stack on logging in successfully.
 */
private fun NavHostController.navigate(
    destination: SurveysDestination,
    parcel: Pair<String, Any?>? = null,
) {
    when (destination) {
        is SurveysDestination.Up -> navigateUp()
        else -> {
            parcel?.let { (key, value) ->
                currentBackStackEntry?.savedStateHandle?.set(key, value)
            }
            navigate(route = destination.destination)
        }
    }
}
