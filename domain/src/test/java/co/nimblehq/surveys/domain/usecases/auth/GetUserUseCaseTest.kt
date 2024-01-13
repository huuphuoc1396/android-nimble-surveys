package co.nimblehq.surveys.domain.usecases.auth

import co.nimblehq.surveys.domain.errors.mappers.remote.RemoteErrorMapper
import co.nimblehq.surveys.domain.models.user.UserModel
import co.nimblehq.surveys.domain.repositories.UserRepository
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
class GetUserUseCaseTest {

    private val userRepository = mockk<UserRepository>()
    private val remoteErrorMapper = mockk<RemoteErrorMapper>()
    private val getUserUseCase = GetUserUseCase(
        userRepository = userRepository,
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
        coEvery { userRepository.getUser() } returns flowOf(userModel)
        getUserUseCase(EmptyParams).collect { result ->
            result.getOrNull() shouldBe userModel
        }
    }
}