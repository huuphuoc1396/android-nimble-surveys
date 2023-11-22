package co.nimblehq.surveys.domain.usecases.auth

import co.nimblehq.surveys.domain.errors.exceptions.network.NetworkCaughtException
import co.nimblehq.surveys.domain.errors.mappers.remote.RemoteErrorMapper
import co.nimblehq.surveys.domain.models.user.UserModel
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
class GetUserUseCaseTest {

    private val authRepository = mockk<AuthRepository>()
    private val remoteErrorMapper = mockk<RemoteErrorMapper>()
    private val getUserUseCase = GetUserUseCase(
        authRepository = authRepository,
        dispatcher = UnconfinedTestDispatcher(),
        remoteErrorMapper = remoteErrorMapper
    )

    @Test
    fun `When get user is successful, it returns UserModel`() = runTest {
        val userModel = UserModel(
            id = "1",
            name = "Tester",
            email = "tester@mail.com",
            avatarUrl = "https://nimble.hq/avatar/1"
        )
        coEvery { authRepository.getUser() } returns userModel
        val result = getUserUseCase(EmptyParams)
        result.getOrNull() shouldBe userModel
    }

    @Test
    fun `When get user is fail, it throw Server error`() = runTest {
        val error = Exception()
        coEvery { authRepository.getUser() } throws error

        val serverError = NetworkCaughtException.Server(401, "")
        every { remoteErrorMapper.map(error) } returns serverError

        val result = getUserUseCase(EmptyParams)
        result.exceptionOrNull() shouldBe serverError
    }
}