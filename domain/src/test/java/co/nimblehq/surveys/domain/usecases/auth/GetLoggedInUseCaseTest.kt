package co.nimblehq.surveys.domain.usecases.auth

import co.nimblehq.surveys.domain.repositories.AuthRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class GetLoggedInUseCaseTest {
    private val authRepository = mockk<AuthRepository>()
    private val getLoggedInUseCase =
        GetLoggedInUseCase(
            authRepository = authRepository,
            dispatcher = UnconfinedTestDispatcher(),
        )

    @Test
    fun `When user logged in, it emits true`() =
        runTest {
            coEvery { authRepository.isLoggedIn() } returns flowOf(true)
            getLoggedInUseCase(Unit).collect { result ->
                result.getOrNull() shouldBe true
            }
        }

    @Test
    fun `When user didn't log in, it emits false`() =
        runTest {
            coEvery { authRepository.isLoggedIn() } returns flowOf(false)
            getLoggedInUseCase(Unit).collect { result ->
                result.getOrNull() shouldBe false
            }
        }
}
