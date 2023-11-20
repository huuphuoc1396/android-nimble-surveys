package co.nimblehq.surveys.domain.repositories

import co.nimblehq.surveys.domain.models.UserModel
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(email: String, password: String): Boolean

    suspend fun logout()

    suspend fun getUser(): UserModel?

    fun isLoggedIn(): Flow<Boolean>
}