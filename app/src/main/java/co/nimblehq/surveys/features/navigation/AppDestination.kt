package co.nimblehq.surveys.features.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class AppDestination(
    val route: String = "",
) {

    companion object {
        const val KEY_ID = "id"
    }

    open val arguments: List<NamedNavArgument> = emptyList()

    open var destination: String = route

    open var parcelableArgument: Pair<String, Any?> = "" to null

    data object Up : AppDestination()

    data object Splash : AppDestination("splash")

    data object Login : AppDestination("login")

    data object Home : AppDestination("home")

    data object ForgotPassword : AppDestination("forgot_password")

    data object SurveyDetail : AppDestination("survey_detail/{$KEY_ID}") {

        override val arguments = listOf(
            navArgument(KEY_ID) { type = NavType.StringType }
        )

        fun createRoute(id: String) = apply {
            destination = "survey_detail/$id"
        }
    }
}
