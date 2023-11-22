package co.nimblehq.surveys.domain.usecases.auth

import co.nimblehq.surveys.domain.errors.exceptions.network.NetworkCaughtException
import co.nimblehq.surveys.domain.errors.mappers.remote.RemoteErrorMapper
import co.nimblehq.surveys.domain.repositories.AuthRepository
import co.nimblehq.surveys.domain.usecases.EmptyParams
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LogoutUseCaseTest {

    private val authRepository = mockk<AuthRepository>()
    private val remoteErrorMapper = mockk<RemoteErrorMapper>()
    private val logoutUseCase = LogoutUseCase(
        authRepository = authRepository,
        dispatcher = UnconfinedTestDispatcher(),
        remoteErrorMapper = remoteErrorMapper
    )

    @Test
    fun `When logout is successful, it returns Unit`() = runTest {
        coEvery { authRepository.logout() } returns Unit
        val result = logoutUseCase(EmptyParams)
        result.getOrNull() shouldBe Unit
    }

    @Test
    fun `When logout is fail, it throw Server error`() = runTest {
        val error = Exception()
        coEvery { authRepository.logout() } throws error

        val serverError = NetworkCaughtException.Server(401, "")
        every { remoteErrorMapper.map(error) } returns serverError

        val result = logoutUseCase(EmptyParams)
        result.exceptionOrNull() shouldBe serverError
    }
}