package co.nimblehq.surveys.data.repositories

import co.nimblehq.surveys.data.services.NonAuthApiService
import co.nimblehq.surveys.data.services.requests.ClientRequestFactory
import co.nimblehq.surveys.data.storages.datastores.EncryptedPrefsDatastore
import co.nimblehq.surveys.data.storages.datastores.EncryptedUserDatastore
import co.nimblehq.surveys.domain.extensions.defaultEmpty
import co.nimblehq.surveys.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val clientRequestFactory: ClientRequestFactory,
        private val nonAuthApiService: NonAuthApiService,
        private val encryptedPrefsDatastore: EncryptedPrefsDatastore,
        private val encryptedUserDatastore: EncryptedUserDatastore,
    ) : AuthRepository {
        override suspend fun login(
            email: String,
            password: String,
        ): Boolean {
            val request = clientRequestFactory.createLoginRequest(email, password)
            val data = nonAuthApiService.login(request).data
            val isLoggedIn = data != null
            if (isLoggedIn) {
                encryptedPrefsDatastore.setTokenType(data?.attributes?.tokenType.defaultEmpty())
                encryptedPrefsDatastore.setAccessToken(data?.attributes?.accessToken.defaultEmpty())
                encryptedPrefsDatastore.setRefreshToken(data?.attributes?.refreshToken.defaultEmpty())
            }
            encryptedPrefsDatastore.setLoggedIn(isLoggedIn)
            return isLoggedIn
        }

        override fun isLoggedIn(): Flow<Boolean> {
            return encryptedPrefsDatastore.isLoggedIn
        }

        override suspend fun logout() {
            val token = encryptedPrefsDatastore.accessToken.firstOrNull().defaultEmpty()
            val request = clientRequestFactory.createLogoutRequest(token)
            nonAuthApiService.logout(request)
            encryptedPrefsDatastore.clearAll()
            encryptedUserDatastore.clearAll()
        }
    }
