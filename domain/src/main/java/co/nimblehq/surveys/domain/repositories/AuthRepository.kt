package co.nimblehq.surveys.domain.repositories

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(
        email: String,
        password: String,
    ): Boolean

    suspend fun logout()

    fun isLoggedIn(): Flow<Boolean>
}
