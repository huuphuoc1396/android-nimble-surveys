package co.nimblehq.surveys.features.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

const val KEY_ID = "id"

sealed class Destination(
    val route: String = "",
) {

    open val arguments: List<NamedNavArgument> = emptyList()

    open var destination: String = route

    open var parcelableArgument: Pair<String, Any?> = "" to null

    data object Up : Destination()

    data object Splash : Destination("splash")

    data object Login : Destination("login")

    data object Home : Destination("home")

    data object ForgotPassword : Destination("forgot_password")

    data object SurveyDetail : Destination("survey_detail/{$KEY_ID}") {

        override val arguments = listOf(
            navArgument(KEY_ID) { type = NavType.StringType }
        )

        fun createRoute(id: String) = apply {
            destination = "second/$id"
        }
    }
}
