package co.nimblehq.surveys.domain.repositories

import co.nimblehq.surveys.domain.models.user.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun fetchUser()

    fun getUser(): Flow<UserModel>
}