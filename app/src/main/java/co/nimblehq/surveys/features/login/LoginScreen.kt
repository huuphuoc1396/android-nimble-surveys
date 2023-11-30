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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.nimblehq.surveys.R
import co.nimblehq.surveys.extensions.collectErrorEffect
import co.nimblehq.surveys.extensions.collectEventEffect
import co.nimblehq.surveys.extensions.collectLoadingWithLifecycle
import co.nimblehq.surveys.extensions.collectUiStateWithLifecycle
import co.nimblehq.surveys.features.components.CustomTextField
import co.nimblehq.surveys.features.components.LoadingContent
import co.nimblehq.surveys.features.error.showToast
import co.nimblehq.surveys.features.navigation.AppDestination
import co.nimblehq.surveys.features.navigation.navigate
import co.nimblehq.surveys.ui.theme.SurveysTheme

object LoginTestTag {
    const val TAG_EDIT_EMAIL = "edit_email"
    const val TAG_EDIT_PASSWORD = "edit_password"
    const val TAG_BUTTON_LOGIN = "button_login"
}

@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: LoginViewModel = hiltViewModel()
) {
    viewModel.collectEventEffect { event ->
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

    val context = LocalContext.current
    viewModel.collectErrorEffect { throwable ->
        throwable.showToast(context)
    }

    val isLoading by viewModel.collectLoadingWithLifecycle()
    val uiState by viewModel.collectUiStateWithLifecycle()
    LoadingContent(isLoading) {
        LoginContent(
            uiState = uiState,
            onEmailChanged = viewModel::onEmailChanged,
            onPasswordChanged = viewModel::onPasswordChanged,
            onLoginClick = viewModel::onLoginClick,
            onForgotClick = viewModel::onForgotClick
        )
    }
}

@Composable
private fun LoginContent(
    uiState: LoginViewModel.UiState = LoginViewModel.UiState(),
    onEmailChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    onLoginClick: () -> Unit = {},
    onForgotClick: () -> Unit = {},
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
                modifier = Modifier.testTag(LoginTestTag.TAG_EDIT_EMAIL),
                hint = stringResource(R.string.email),
                value = uiState.email,
                onValueChange = onEmailChanged,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
            )
            Spacer(modifier = Modifier.size(20.dp))
            Box(
                contentAlignment = Alignment.CenterEnd,
            ) {
                CustomTextField(
                    modifier = Modifier.testTag(LoginTestTag.TAG_EDIT_PASSWORD),
                    hint = stringResource(R.string.password),
                    value = uiState.password,
                    onValueChange = onPasswordChanged,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Go,
                    ),
                    keyboardActions = KeyboardActions(onGo = {
                        onLoginClick()
                    })
                )
                TextButton(
                    onClick = onForgotClick,
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
                    .testTag(LoginTestTag.TAG_BUTTON_LOGIN)
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = onLoginClick,
                enabled = uiState.isLoginEnabled,
            ) {
                Text(text = stringResource(R.string.login))
            }
        }
    }
}


@Preview
@Composable
fun LoginContentPreview() {
    SurveysTheme {
        LoginContent()
    }
}
