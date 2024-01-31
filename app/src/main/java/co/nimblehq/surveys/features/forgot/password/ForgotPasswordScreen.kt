package co.nimblehq.surveys.features.forgot.password

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.nimblehq.surveys.ui.theme.SurveysTheme

@Composable
fun ForgotPasswordScreen(navController: NavHostController = rememberNavController()) {
    Box(
        modifier =
            Modifier
                .statusBarsPadding()
                .fillMaxSize(),
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = "Forgot Password",
            style = MaterialTheme.typography.h3,
        )
    }
}

@Preview
@Composable
fun ForgotPasswordScreenPreview() {
    SurveysTheme {
        ForgotPasswordScreen()
    }
}
