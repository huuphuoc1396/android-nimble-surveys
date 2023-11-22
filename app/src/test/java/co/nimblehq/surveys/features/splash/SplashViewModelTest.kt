package co.nimblehq.surveys.features.splash

import app.cash.turbine.test
import co.nimblehq.surveys.ViewModelTest
import co.nimblehq.surveys.domain.functional.wrapSuccess
import co.nimblehq.surveys.domain.usecases.EmptyParams
import co.nimblehq.surveys.domain.usecases.auth.GetLoggedInUseCase
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class SplashViewModelTest : ViewModelTest() {

    private val getLoggedInUseCase: GetLoggedInUseCase = mockk()
    private lateinit var viewModel: SplashViewModel

    override fun setUp() {
        super.setUp()
        viewModel = SplashViewModel(getLoggedInUseCase)
    }

    @Test
    fun `When user have logged in, it waits Splash delay then go to Home`() = runTest {
        coEvery { getLoggedInUseCase(EmptyParams) } returns flowOf(true.wrapSuccess())
        viewModel.checkLoggedIn()
        viewModel.singleEvents.test {
            advanceUntilIdle()
            expectMostRecentItem() shouldBe SplashViewModel.Event.GoToHome
        }
    }

    @Test
    fun `When user haven't logged in yet, it waits Splash delay then go to Login`() = runTest {
        coEvery { getLoggedInUseCase(EmptyParams) } returns flowOf(false.wrapSuccess())
        viewModel.checkLoggedIn()
        viewModel.singleEvents.test {
            advanceUntilIdle()
            expectMostRecentItem() shouldBe SplashViewModel.Event.GoToLogin
        }
    }
}