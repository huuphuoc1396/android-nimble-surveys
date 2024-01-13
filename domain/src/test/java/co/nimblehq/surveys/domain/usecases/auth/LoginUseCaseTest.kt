package co.nimblehq.surveys.domain.usecases.auth

import co.nimblehq.surveys.domain.models.errors.ApiError
import co.nimblehq.surveys.domain.errors.mappers.remote.RemoteErrorMapper
import co.nimblehq.surveys.domain.repositories.AuthRepository
import co.nimblehq.surveys.domain.repositories.UserRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginUseCaseTest {

    private val authRepository = mockk<AuthRepository>()
    private val userRepository = mockk<UserRepository>()
    private val remoteErrorMapper = mockk<RemoteErrorMapper>()
    private val loginUseCase = LoginUseCase(
        authRepository = authRepository,
        userRepository = userRepository,
        dispatcher = UnconfinedTestDispatcher(),
        remoteErrorMapper = remoteErrorMapper,
    )

    @Test
    fun `When login is successful, it returns true`() = runTest {
        coEvery { authRepository.login(email = "tester@mail.com", password = "1234") } returns true
        coEvery { userRepository.fetchUser() } returns Unit
        val params = LoginUseCase.Params(email = "tester@mail.com", password = "1234")
        val result = loginUseCase(params)
        result.getOrNull() shouldBe true
    }

    @Test
    fun `When login is fail, it throw Server error`() = runTest {
        val error = Exception()
        coEvery {
            authRepository.login(
                email = "tester@mail.com",
                password = "1234"
            )
        } throws error

        val serverError = ApiError.Server(401, "")
        every { remoteErrorMapper.map(error) } returns serverError

        val params = LoginUseCase.Params(email = "tester@mail.com", password = "1234")
        val result = loginUseCase(params)
        result.exceptionOrNull() shouldBe serverError
    }
}