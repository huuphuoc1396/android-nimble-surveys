package co.nimblehq.surveys.data.repositories

import co.nimblehq.surveys.data.services.NonAuthApiService
import co.nimblehq.surveys.data.services.requests.ClientRequestFactory
import co.nimblehq.surveys.data.services.requests.login.LoginRequest
import co.nimblehq.surveys.data.services.requests.logout.LogoutRequest
import co.nimblehq.surveys.data.services.responses.DataResponse
import co.nimblehq.surveys.data.services.responses.login.Attributes
import co.nimblehq.surveys.data.services.responses.login.LoginResponse
import co.nimblehq.surveys.data.storages.datastores.EncryptedPrefsDatastore
import co.nimblehq.surveys.data.storages.datastores.EncryptedUserDatastore
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class AuthRepositoryImplTest {
    private val clientRequestFactory: ClientRequestFactory = mockk()
    private val nonAuthApiService: NonAuthApiService = mockk()
    private val encryptedPrefsDatastore: EncryptedPrefsDatastore = mockk()
    private val encryptedUserDatastore: EncryptedUserDatastore = mockk()

    private val repositoryImpl =
        AuthRepositoryImpl(
            clientRequestFactory = clientRequestFactory,
            nonAuthApiService = nonAuthApiService,
            encryptedPrefsDatastore = encryptedPrefsDatastore,
            encryptedUserDatastore = encryptedUserDatastore,
        )

    @Test
    fun `When login is successful, it saves tokens to storage and returns true`() =
        runTest {
            val tokenType = "Bear"
            val accessToken = "92yE8gBkoh1bK7I1p0Dd5vV0xVVUT5uIcttj2LQbcFiCTjGFdz50fgQ7z0MwwkZw"
            val refreshToken = "V8iFciCVDwRJflepctO35HGPqZuxZ4B5ZeIKtYg3SLH1qRYI4yZCRT6wbeUCjjbr"
            val loginRequest =
                LoginRequest(
                    email = "tester@mail.com",
                    password = "1234",
                    grantType = "password",
                    clientId = "",
                    clientSecret = "",
                )
            val loginResponse =
                LoginResponse(
                    attributes =
                        Attributes(
                            accessToken = accessToken,
                            createdAt = null,
                            expiresIn = null,
                            refreshToken = refreshToken,
                            tokenType = tokenType,
                        ),
                    id = "1",
                    type = "password",
                )
            every {
                clientRequestFactory.createLoginRequest(
                    email = "tester@mail.com",
                    password = "1234",
                )
            } returns loginRequest
            coEvery { nonAuthApiService.login(loginRequest) } returns DataResponse(loginResponse)
            coEvery { encryptedPrefsDatastore.setTokenType(tokenType) } returns Unit
            coEvery { encryptedPrefsDatastore.setAccessToken(accessToken) } returns Unit
            coEvery { encryptedPrefsDatastore.setRefreshToken(refreshToken) } returns Unit
            coEvery { encryptedPrefsDatastore.setLoggedIn(true) } returns Unit
            repositoryImpl.login("tester@mail.com", "1234") shouldBe true
        }

    @Test
    fun `When login responses data null, it returns false`() =
        runTest {
            val loginRequest =
                LoginRequest(
                    email = "tester@mail.com",
                    password = "1234",
                    grantType = "password",
                    clientId = "",
                    clientSecret = "",
                )
            every {
                clientRequestFactory.createLoginRequest(
                    email = "tester@mail.com",
                    password = "1234",
                )
            } returns loginRequest
            coEvery { nonAuthApiService.login(loginRequest) } returns DataResponse(null)
            coEvery { encryptedPrefsDatastore.setLoggedIn(false) } returns Unit
            repositoryImpl.login("tester@mail.com", "1234") shouldBe false
        }

    @Test
    fun `When login is fail, it throw error`() =
        runTest {
            val error = Exception()
            val loginRequest =
                LoginRequest(
                    email = "tester@mail.com",
                    password = "1234",
                    grantType = "password",
                    clientId = "",
                    clientSecret = "",
                )
            every {
                clientRequestFactory.createLoginRequest(
                    email = "tester@mail.com",
                    password = "1234",
                )
            } returns loginRequest
            coEvery { nonAuthApiService.login(loginRequest) } throws error
            coEvery { encryptedPrefsDatastore.setLoggedIn(false) } returns Unit
            try {
                repositoryImpl.login("tester@mail.com", "1234")
            } catch (e: Exception) {
                e shouldBe error
            }
        }

    @Test
    fun `When logout is successful, it returns Unit`() =
        runTest {
            val accessToken = "92yE8gBkoh1bK7I1p0Dd5vV0xVVUT5uIcttj2LQbcFiCTjGFdz50fgQ7z0MwwkZw"
            val logoutRequest =
                LogoutRequest(
                    token = accessToken,
                    clientId = "",
                    clientSecret = "",
                )
            every { clientRequestFactory.createLogoutRequest(accessToken) } returns logoutRequest
            coEvery { nonAuthApiService.logout(request = logoutRequest) } returns Unit
            coEvery { encryptedPrefsDatastore.accessToken } returns flowOf(accessToken)
            coEvery { encryptedPrefsDatastore.clearAll() } returns Unit
            coEvery { encryptedUserDatastore.clearAll() } returns Unit
            repositoryImpl.logout() shouldBe Unit
        }

    @Test
    fun `When logout is fail, it throws error`() =
        runTest {
            val error = Exception()
            val accessToken = "92yE8gBkoh1bK7I1p0Dd5vV0xVVUT5uIcttj2LQbcFiCTjGFdz50fgQ7z0MwwkZw"
            val logoutRequest =
                LogoutRequest(
                    token = accessToken,
                    clientId = "",
                    clientSecret = "",
                )
            every { clientRequestFactory.createLogoutRequest(accessToken) } returns logoutRequest
            coEvery { nonAuthApiService.logout(logoutRequest) } throws error
            coEvery { encryptedPrefsDatastore.accessToken } returns flowOf(accessToken)
            coEvery { encryptedPrefsDatastore.clearAll() } returns Unit
            try {
                repositoryImpl.logout()
            } catch (e: Exception) {
                e shouldBe error
            }
        }

    @Test
    fun `When user logged in, it emits true`() =
        runTest {
            every { encryptedPrefsDatastore.isLoggedIn } returns flowOf(true)
            repositoryImpl.isLoggedIn().collect {
                it shouldBe true
            }
        }

    @Test
    fun `When user didn't login, it emits false`() =
        runTest {
            every { encryptedPrefsDatastore.isLoggedIn } returns flowOf(false)
            repositoryImpl.isLoggedIn().collect {
                it shouldBe false
            }
        }
}
