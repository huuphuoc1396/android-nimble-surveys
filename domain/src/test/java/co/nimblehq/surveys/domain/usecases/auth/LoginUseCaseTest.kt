package co.nimblehq.surveys.domain.usecases.auth

import co.nimblehq.surveys.domain.errors.exceptions.network.NetworkCaughtException
import co.nimblehq.surveys.domain.errors.mappers.remote.RemoteErrorMapper
import co.nimblehq.surveys.domain.repositories.AuthRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginUseCaseTest {

    private val authRepository = mockk<AuthRepository>()
    private val remoteErrorMapper = mockk<RemoteErrorMapper>()
    private val loginUseCase = LoginUseCase(
        authRepository = authRepository,
        dispatcher = UnconfinedTestDispatcher(),
        remoteErrorMapper = remoteErrorMapper
    )

    @Test
    fun `When login is successful, it returns true`() = runTest {
        coEvery { authRepository.login(email = "tester@mail.com", password = "1234") } returns true
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

        val serverError = NetworkCaughtException.Server(401, "")
        every { remoteErrorMapper.map(error) } returns serverError

        val params = LoginUseCase.Params(email = "tester@mail.com", password = "1234")
        val result = loginUseCase(params)
        result.exceptionOrNull() shouldBe serverError
    }
}