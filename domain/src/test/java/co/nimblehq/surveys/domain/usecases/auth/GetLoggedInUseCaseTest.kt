package co.nimblehq.surveys.domain.usecases.auth

import co.nimblehq.surveys.domain.errors.mappers.remote.RemoteErrorMapper
import co.nimblehq.surveys.domain.repositories.AuthRepository
import co.nimblehq.surveys.domain.usecases.EmptyParams
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class GetLoggedInUseCaseTest {

    private val authRepository = mockk<AuthRepository>()
    private val remoteErrorMapper = mockk<RemoteErrorMapper>()
    private val getLoggedInUseCase = GetLoggedInUseCase(
        authRepository = authRepository,
        dispatcher = UnconfinedTestDispatcher(),
        remoteErrorMapper = remoteErrorMapper
    )

    @Test
    fun `When user logged in, it emits true`() = runTest {
        coEvery { authRepository.isLoggedIn() } returns flowOf(true)
        getLoggedInUseCase(EmptyParams).collect { result ->
            result.getOrNull() shouldBe true
        }
    }

    @Test
    fun `When user didn't log in, it emits false`() = runTest {
        coEvery { authRepository.isLoggedIn() } returns flowOf(false)
        getLoggedInUseCase(EmptyParams).collect { result ->
            result.getOrNull() shouldBe false
        }
    }
}