package co.nimblehq.surveys.data.repositories

import co.nimblehq.surveys.domain.models.UserModel
import co.nimblehq.surveys.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {

    override suspend fun login(email: String, password: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }

    override fun getUser(): Flow<UserModel> {
        TODO("Not yet implemented")
    }
}