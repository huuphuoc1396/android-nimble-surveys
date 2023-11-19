package co.nimblehq.surveys.features.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

const val KEY_ID = "id"

sealed class SurveysDestination(
    val route: String = "",
) {

    open val arguments: List<NamedNavArgument> = emptyList()

    open var destination: String = route

    open var parcelableArgument: Pair<String, Any?> = "" to null

    data object Up : SurveysDestination()

    data object Splash : SurveysDestination("splash")

    data object Login : SurveysDestination("login")

    data object Home : SurveysDestination("home")

    data object ForgotPassword : SurveysDestination("forgot_password")

    data object SurveyDetail : SurveysDestination("survey_detail/{$KEY_ID}") {

        override val arguments = listOf(
            navArgument(KEY_ID) { type = NavType.StringType }
        )

        fun createRoute(id: String) = apply {
            destination = "second/$id"
        }
    }
}
