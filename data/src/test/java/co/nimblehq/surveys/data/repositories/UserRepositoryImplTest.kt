package co.nimblehq.surveys.data.repositories

import co.nimblehq.surveys.data.services.AuthApiService
import co.nimblehq.surveys.data.services.responses.DataResponse
import co.nimblehq.surveys.data.services.responses.user.Attributes
import co.nimblehq.surveys.data.services.responses.user.UserResponse
import co.nimblehq.surveys.data.storages.datastores.EncryptedUserDatastore
import co.nimblehq.surveys.domain.models.user.UserModel
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class UserRepositoryImplTest {
    private val authApiService: AuthApiService = mockk()
    private val encryptedUserDatastore: EncryptedUserDatastore = mockk()
    private val repositoryImpl =
        UserRepositoryImpl(
            authApiService = authApiService,
            encryptedUserDatastore = encryptedUserDatastore,
        )

    @Test
    fun `When fetch user is successful, it saves user to the storage`() =
        runTest {
            coEvery {
                authApiService.getUser()
            } returns
                DataResponse(
                    data =
                        UserResponse(
                            attributes =
                                Attributes(
                                    avatarUrl = "https://nimbel.hq/avatar/1",
                                    email = "tester@mail.com",
                                    name = "Tester",
                                ),
                            id = "1",
                            type = "user",
                        ),
                )
            coEvery {
                encryptedUserDatastore.saveUser(
                    UserModel(
                        id = "1",
                        name = "Tester",
                        email = "tester@mail.com",
                        avatarUrl = "https://nimbel.hq/avatar/1",
                    ),
                )
            } returns Unit
            repositoryImpl.fetchUser() shouldBe Unit
        }

    @Test
    fun `When fetch user is fail, it throws error`() =
        runTest {
            val error = Exception()
            coEvery {
                authApiService.getUser()
            } throws error
            try {
                repositoryImpl.fetchUser()
            } catch (e: Exception) {
                e shouldBe error
            }
        }

    @Test
    fun `When get user is successfully, it emit UserModel`() =
        runTest {
            val userModel =
                UserModel(
                    id = "1",
                    name = "Tester",
                    email = "tester@mail.com",
                    avatarUrl = "https://nimbel.hq/avatar/1",
                )
            coEvery {
                encryptedUserDatastore.getUser()
            } returns flowOf(userModel)
            repositoryImpl.getUser().collect {
                it shouldBe userModel
            }
        }
}
