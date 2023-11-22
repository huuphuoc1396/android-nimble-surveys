package co.nimblehq.surveys.data.storages.datastore

import co.nimblehq.surveys.domain.models.user.UserModel
import kotlinx.coroutines.flow.Flow

interface EncryptedUserDatastore {
    suspend fun saveUser(userModel: UserModel)

    fun getUser(): Flow<UserModel>
}