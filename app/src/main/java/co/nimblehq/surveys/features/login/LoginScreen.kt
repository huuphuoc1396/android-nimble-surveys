package co.nimblehq.surveys.features.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.nimblehq.surveys.R
import co.nimblehq.surveys.extensions.uistate.collectEvent
import co.nimblehq.surveys.extensions.uistate.collectWithLifecycle
import co.nimblehq.surveys.features.components.CustomTextField
import co.nimblehq.surveys.features.components.LoadingContent
import co.nimblehq.surveys.features.navigation.AppDestination
import co.nimblehq.surveys.features.navigation.navigate
import co.nimblehq.surveys.ui.theme.SurveysTheme

@Composable
fun LoginScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: LoginViewModel = hiltViewModel(),
    lifecycle: LifecycleOwner = LocalLifecycleOwner.current
) {
    val uiState by viewModel.collectWithLifecycle()
    LaunchedEffect(key1 = Unit) {
        viewModel.collectEvent(lifecycle) { event ->
            when (event) {
                LoginViewModel.Event.GoToHome -> {
                    navController.navigate(AppDestination.Home) {
                        popUpTo(AppDestination.Login.route) { inclusive = true }
                    }
                }

                LoginViewModel.Event.GoToForgotPassword -> {
                    navController.navigate(AppDestination.ForgotPassword)
                }
            }
        }
    }

    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    LoadingContent(isLoading = isLoading) {
        LoginContent(uiState, viewModel)
    }
}

@Composable
private fun LoginContent(
    uiState: LoginViewModel.UiState,
    viewModel: LoginViewModel
) {
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.bg_blur_overlay),
                contentScale = ContentScale.Crop,
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier.size(168.dp, 40.dp),
                painter = painterResource(id = R.drawable.ic_nimble_logo),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.size(109.dp))
            CustomTextField(
                hint = stringResource(R.string.email),
                value = uiState.email,
                onValueChange = viewModel::onEmailChanged,
            )
            Spacer(modifier = Modifier.size(20.dp))
            Box(
                contentAlignment = Alignment.CenterEnd,
            ) {
                CustomTextField(
                    hint = stringResource(R.string.password),
                    value = uiState.password,
                    onValueChange = viewModel::onPasswordChanged,
                )
                TextButton(
                    onClick = viewModel::onForgotClick,
                ) {
                    Text(
                        text = stringResource(R.string.forgot_question),
                        style = MaterialTheme.typography.body2.copy(
                            color = Color.White.copy(alpha = 0.5f)
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.size(20.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = viewModel::onLoginClick,
                enabled = uiState.isLoginEnabled,
            ) {
                Text(text = stringResource(R.string.login))
            }
        }
    }
}


@Preview
@Composable
fun LoginScreenPreview() {
    SurveysTheme {
        LoginScreen()
    }
}
