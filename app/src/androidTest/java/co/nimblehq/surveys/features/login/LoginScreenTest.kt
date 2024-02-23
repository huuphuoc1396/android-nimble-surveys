package co.nimblehq.surveys.features.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavHostController
import co.nimblehq.surveys.domain.functional.wrapFailure
import co.nimblehq.surveys.domain.functional.wrapSuccess
import co.nimblehq.surveys.domain.usecases.auth.LoginUseCase
import co.nimblehq.surveys.features.navigation.AppDestination
import co.nimblehq.surveys.features.navigation.navigate
import co.nimblehq.surveys.ui.theme.SurveysTheme
import de.mannodermaus.junit5.compose.createComposeExtension
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalTestApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
class LoginScreenTest {
    @JvmField
    val extension = createComposeExtension()
    private val navController = mockk<NavHostController>()
    private val loginUseCase = mockk<LoginUseCase>()
    private val loginViewModel = LoginViewModel(loginUseCase)

    @BeforeEach
    fun beforeEach() {
        extension.use {
            setContent {
                SurveysTheme {
                    LoginScreen(
                        navController = navController,
                        viewModel = loginViewModel,
                    )
                }
            }
        }
    }

    @Test
    fun test_ShowLoginScreen() {
        extension.use {
            onNodeWithText("Login")

            onNodeWithTag(LoginTestTag.TAG_BUTTON_LOGIN)
                .assertIsNotEnabled()
        }
    }

    @Test
    fun test_LoginSuccess_Then_NavigateToHome() {
        extension.use {
            coEvery {
                val params = LoginUseCase.Params("tester@mail.com", "12345678")
                loginUseCase(params)
            } returns true.wrapSuccess()

            coEvery { navController.navigate(AppDestination.Home, any()) } returns Unit

            onNodeWithTag(LoginTestTag.TAG_EDIT_EMAIL)
                .performTextInput("tester@mail.com")

            onNodeWithTag(LoginTestTag.TAG_EDIT_PASSWORD)
                .performTextInput("12345678")

            onNodeWithTag(LoginTestTag.TAG_BUTTON_LOGIN)
                .assertIsEnabled()
                .performClick()

            verify { navController.navigate(AppDestination.Home, any()) }
        }
    }

    @Test
    fun test_LoginFail_Then_ShowToastError() {
        extension.use {
            val error = Throwable("Error")
            coEvery {
                val params = LoginUseCase.Params("tester@mail.com", "12345678")
                loginUseCase(params)
            } returns error.wrapFailure()

            onNodeWithTag(LoginTestTag.TAG_EDIT_EMAIL)
                .performTextInput("tester@mail.com")

            onNodeWithTag(LoginTestTag.TAG_EDIT_PASSWORD)
                .performTextInput("12345678")

            onNodeWithTag(LoginTestTag.TAG_BUTTON_LOGIN)
                .assertIsEnabled()
                .performClick()

            onNodeWithText("Error")
        }
    }
}
