package co.nimblehq.surveys.features.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.nimblehq.surveys.features.navigation.SurveysDestination
import co.nimblehq.surveys.ui.theme.SurveysTheme

@Composable
fun LoginScreen(navigator: (destination: SurveysDestination) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Login Screen",
            style = MaterialTheme.typography.h3,
        )
    }
}


@Preview
@Composable
fun LoginScreenPreview() {
    SurveysTheme {
        LoginScreen(navigator = {})
    }
}
