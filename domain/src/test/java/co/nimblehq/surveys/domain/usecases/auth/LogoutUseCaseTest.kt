package co.nimblehq.surveys.domain.usecases.auth

import co.nimblehq.surveys.domain.repositories.AuthRepository
import co.nimblehq.surveys.domain.usecases.EmptyParams
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class LogoutUseCaseTest {
    private val authRepository = mockk<AuthRepository>()
    private val logoutUseCase =
        LogoutUseCase(
            authRepository = authRepository,
            dispatcher = UnconfinedTestDispatcher(),
        )

    @Test
    fun `When logout is successful, it returns Unit`() =
        runTest {
            coEvery { authRepository.logout() } returns Unit
            val result = logoutUseCase(EmptyParams)
            result.getOrNull() shouldBe Unit
        }

    @Test
    fun `When logout is fail, it throw error`() =
        runTest {
            val error = Exception()
            coEvery { authRepository.logout() } throws error

            val result = logoutUseCase(EmptyParams)
            result.exceptionOrNull() shouldBe error
        }
}
