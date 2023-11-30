package co.nimblehq.surveys.features.login

import app.cash.turbine.test
import co.nimblehq.surveys.ViewModelTest
import co.nimblehq.surveys.domain.functional.wrapFailure
import co.nimblehq.surveys.domain.functional.wrapSuccess
import co.nimblehq.surveys.domain.usecases.auth.LoginUseCase
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest : ViewModelTest() {

    private val loginUseCase: LoginUseCase = mockk()
    private lateinit var loginViewModel: LoginViewModel

    override fun setUp() {
        super.setUp()
        loginViewModel = LoginViewModel(loginUseCase)
    }

    @Test
    fun `When email and password valid, it enables login button`() = runTest {
        loginViewModel.onEmailChanged("tester@mail.com")
        loginViewModel.onPasswordChanged("12345678")
        loginViewModel.uiStateFlow.test {
            expectMostRecentItem().isLoginEnabled shouldBe true
        }
    }

    @Test
    fun `When email and password invalid, it disables login button`() = runTest {
        loginViewModel.onEmailChanged("")
        loginViewModel.onPasswordChanged("")
        loginViewModel.uiStateFlow.test {
            expectMostRecentItem().isLoginEnabled shouldBe false
        }
    }

    @Test
    fun `When email changed, it updates email in the UiState`() = runTest {
        loginViewModel.onEmailChanged("tester@mail.com")
        loginViewModel.uiStateFlow.test {
            expectMostRecentItem().email shouldBe "tester@mail.com"
        }
    }

    @Test
    fun `When password changed, it updates password in the UiState`() = runTest {
        loginViewModel.onPasswordChanged("12345678")
        loginViewModel.uiStateFlow.test {
            expectMostRecentItem().password shouldBe "12345678"
        }
    }

    @Test
    fun `When login click, it logins successfully then sends GoToHome event `() = runTest {
        loginViewModel.onEmailChanged("tester@mail.com")
        loginViewModel.onPasswordChanged("12345678")
        coEvery {
            val params = LoginUseCase.Params("tester@mail.com", "12345678")
            loginUseCase(params)
        } returns true.wrapSuccess()
        loginViewModel.onLoginClick()
        loginViewModel.singleEvents.test {
            expectMostRecentItem() shouldBe LoginViewModel.Event.GoToHome
        }
    }

    @Test
    fun `When login click, it logins fail then throw error`() = runTest {
        val error = Throwable()
        loginViewModel.onEmailChanged("tester@mail.com")
        loginViewModel.onPasswordChanged("12345678")
        coEvery {
            val params = LoginUseCase.Params("tester@mail.com", "12345678")
            loginUseCase(params)
        } returns error.wrapFailure()
        loginViewModel.onLoginClick()
        loginViewModel.error.test {
            expectMostRecentItem() shouldBe error
        }
    }

    @Test
    fun `When forgot password click, it sends GoToForgotPassword event`() = runTest {
        loginViewModel.onForgotClick()
        loginViewModel.singleEvents.test {
            expectMostRecentItem() is LoginViewModel.Event.GoToForgotPassword
        }
    }
}