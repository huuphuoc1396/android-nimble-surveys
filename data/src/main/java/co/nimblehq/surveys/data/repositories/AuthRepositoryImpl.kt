package co.nimblehq.surveys.data.repositories

import co.nimblehq.surveys.data.services.AuthApiService
import co.nimblehq.surveys.data.services.NonAuthApiService
import co.nimblehq.surveys.data.services.requests.login.LoginRequest
import co.nimblehq.surveys.data.services.responses.user.toUserModel
import co.nimblehq.surveys.data.storages.datastore.EncryptedPrefsDatastore
import co.nimblehq.surveys.domain.extensions.defaultEmpty
import co.nimblehq.surveys.domain.models.UserModel
import co.nimblehq.surveys.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val nonAuthApiService: NonAuthApiService,
    private val authApiService: AuthApiService,
    private val encryptedPrefsDatastore: EncryptedPrefsDatastore,
) : AuthRepository {

    override suspend fun login(email: String, password: String): Boolean {
        val data = nonAuthApiService.login(LoginRequest(email, password)).data
        val isLoggedIn = data != null
        if (isLoggedIn) {
            encryptedPrefsDatastore.setAccessToken(data?.attributes?.authToken.defaultEmpty())
            encryptedPrefsDatastore.setRefreshToken(data?.attributes?.refreshToken.defaultEmpty())
        }
        encryptedPrefsDatastore.setLoggedIn(isLoggedIn)
        return isLoggedIn
    }

    override fun isLoggedIn(): Flow<Boolean> {
        return encryptedPrefsDatastore.isLoggedIn
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(): UserModel? {
        return authApiService.getUser().data?.toUserModel()
    }
}