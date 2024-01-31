package co.nimblehq.surveys.domain.usecases.auth

import co.nimblehq.surveys.domain.repositories.AuthRepository
import co.nimblehq.surveys.domain.repositories.UserRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class LoginUseCaseTest {
    private val authRepository = mockk<AuthRepository>()
    private val userRepository = mockk<UserRepository>()
    private val loginUseCase =
        LoginUseCase(
            authRepository = authRepository,
            userRepository = userRepository,
            dispatcher = UnconfinedTestDispatcher(),
        )

    @Test
    fun `When login is successful, it returns true`() =
        runTest {
            coEvery { authRepository.login(email = "tester@mail.com", password = "1234") } returns true
            coEvery { userRepository.fetchUser() } returns Unit
            val params = LoginUseCase.Params(email = "tester@mail.com", password = "1234")
            val result = loginUseCase(params)
            result.getOrNull() shouldBe true
        }

    @Test
    fun `When login is fail, it throw error`() =
        runTest {
            val error = Exception()
            coEvery {
                authRepository.login(
                    email = "tester@mail.com",
                    password = "1234",
                )
            } throws error

            val params = LoginUseCase.Params(email = "tester@mail.com", password = "1234")
            val result = loginUseCase(params)
            result.exceptionOrNull() shouldBe error
        }
}
