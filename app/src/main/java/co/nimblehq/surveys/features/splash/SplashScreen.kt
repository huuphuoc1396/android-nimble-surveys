package co.nimblehq.surveys.features.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.nimblehq.surveys.R
import co.nimblehq.surveys.extensions.collectErrorEffect
import co.nimblehq.surveys.extensions.collectEventEffect
import co.nimblehq.surveys.features.error.showToast
import co.nimblehq.surveys.features.navigation.AppDestination
import co.nimblehq.surveys.features.navigation.navigate
import co.nimblehq.surveys.ui.theme.SurveysTheme

@Composable
fun SplashScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: SplashViewModel = hiltViewModel(),
) {
    viewModel.collectEventEffect { event ->
        when (event) {
            SplashViewModel.Event.GoToHome -> {
                navController.navigate(AppDestination.Home) {
                    popUpTo(AppDestination.Splash.route) { inclusive = true }
                }
            }

            SplashViewModel.Event.GoToLogin -> {
                navController.navigate(AppDestination.Login) {
                    popUpTo(AppDestination.Splash.route) { inclusive = true }
                }
            }
        }
    }

    val context = LocalContext.current
    viewModel.collectErrorEffect { throwable ->
        throwable.showToast(context)
    }

    SplashContent()
}

@Composable
private fun SplashContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(202.dp, 48.dp),
            painter = painterResource(id = R.drawable.ic_nimble_logo),
            contentDescription = null,
        )
    }
}

@Preview
@Composable
fun SplashContentPreview() {
    SurveysTheme {
        SplashContent()
    }
}
