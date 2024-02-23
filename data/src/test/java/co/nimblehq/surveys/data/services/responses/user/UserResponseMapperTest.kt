package co.nimblehq.surveys.data.services.responses.user

import co.nimblehq.surveys.domain.models.user.UserModel
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class UserResponseMapperTest {
    @Test
    fun `map UserResponse to UserModel`() {
        val userResponse = UserResponse(
            attributes = Attributes(
                avatarUrl = "https://nimble.hq/avatar/1",
                email = "tester@mail.com",
                name = "Tester"
            ),
            id = "1",
            type = "user",
        )
        userResponse.toUserModel() shouldBe UserModel(
            id = "1",
            name = "Tester",
            email = "tester@mail.com",
            avatarUrl = "https://nimble.hq/avatar/1",
        )
    }
}