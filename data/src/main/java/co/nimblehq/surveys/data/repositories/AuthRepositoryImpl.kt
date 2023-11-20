package co.nimblehq.surveys.data.repositories

import co.nimblehq.surveys.data.services.NonAuthApiService
import co.nimblehq.surveys.data.services.requests.login.LoginRequest
import co.nimblehq.surveys.domain.models.UserModel
import co.nimblehq.surveys.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val nonAuthApiService: NonAuthApiService,
) : AuthRepository {

    override suspend fun login(email: String, password: String): Boolean {
        val data = nonAuthApiService.login(LoginRequest(email, password)).data
        return data != null
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }

    override fun getUser(): Flow<UserModel> {
        TODO("Not yet implemented")
    }
}