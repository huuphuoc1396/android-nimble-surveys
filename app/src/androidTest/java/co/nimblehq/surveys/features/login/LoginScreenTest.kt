package co.nimblehq.surveys.features.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
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
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val navController = mockk<NavHostController>()
    private val loginUseCase = mockk<LoginUseCase>()
    private val loginViewModel = LoginViewModel(loginUseCase)

    private fun setContent() {
        composeTestRule.setContent {
            SurveysTheme {
                LoginScreen(
                    navController = navController,
                    viewModel = loginViewModel,
                )
            }
        }
    }

    @Test
    fun test_ShowLoginScreen() {
        setContent()
        with(composeTestRule) {

            onNodeWithText("Login")

            onNodeWithTag(LoginTestTag.TAG_BUTTON_LOGIN)
                .assertIsNotEnabled()
        }
    }

    @Test
    fun test_LoginSuccess_Then_NavigateToHome() {
        coEvery {
            val params = LoginUseCase.Params("tester@mail.com", "12345678")
            loginUseCase(params)
        } returns true.wrapSuccess()

        coEvery { navController.navigate(AppDestination.Home, any()) } returns Unit

        setContent()
        with(composeTestRule) {

            onNodeWithTag(LoginTestTag.TAG_EDIT_EMAIL)
                .performTextInput("tester@mail.com")

            onNodeWithTag(LoginTestTag.TAG_EDIT_PASSWORD)
                .performTextInput("12345678")

            onNodeWithTag(LoginTestTag.TAG_BUTTON_LOGIN)
                .assertIsEnabled()
                .performClick()
        }

        verify { navController.navigate(AppDestination.Home, any()) }
    }

    @Test
    fun test_LoginFail_Then_ShowToastError() {
        val error = Throwable("Error")
        coEvery {
            val params = LoginUseCase.Params("tester@mail.com", "12345678")
            loginUseCase(params)
        } returns error.wrapFailure()

        setContent()
        with(composeTestRule) {

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