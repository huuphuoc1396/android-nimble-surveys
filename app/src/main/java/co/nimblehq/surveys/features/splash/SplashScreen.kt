package co.nimblehq.surveys.features.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import co.nimblehq.surveys.R
import co.nimblehq.surveys.extensions.uistate.collectEvent
import co.nimblehq.surveys.features.navigation.SurveysDestination
import co.nimblehq.surveys.ui.theme.SurveysTheme

@Composable
fun SplashScreen(
    navigator: (destination: SurveysDestination) -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
    lifecycle: LifecycleOwner = LocalLifecycleOwner.current
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.collectEvent(lifecycle) { event ->
            when (event) {
                SplashViewModel.Event.GoToHome -> {
                    navigator.invoke(SurveysDestination.Home)
                }

                SplashViewModel.Event.GoToLogin -> {
                    navigator.invoke(SurveysDestination.Login)
                }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_nimble_logo),
            contentDescription = null,
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SurveysTheme {
        SplashScreen(navigator = {})
    }
}
