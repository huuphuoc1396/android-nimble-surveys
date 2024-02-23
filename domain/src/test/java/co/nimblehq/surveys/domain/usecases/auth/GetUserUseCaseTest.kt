package co.nimblehq.surveys.domain.usecases.auth

import co.nimblehq.surveys.domain.models.user.UserModel
import co.nimblehq.surveys.domain.repositories.UserRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class GetUserUseCaseTest {

    private val userRepository = mockk<UserRepository>()
    private val getUserUseCase = GetUserUseCase(
        userRepository = userRepository,
        dispatcher = UnconfinedTestDispatcher(),
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
        getUserUseCase(Unit).collect { result ->
            result.getOrNull() shouldBe userModel
        }
    }
}