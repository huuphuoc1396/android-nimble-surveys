package co.nimblehq.surveys.data.repositories

import co.nimblehq.surveys.data.services.AuthApiService
import co.nimblehq.surveys.data.services.responses.user.toUserModel
import co.nimblehq.surveys.data.storages.datastore.EncryptedUserDatastore
import co.nimblehq.surveys.domain.models.user.UserModel
import co.nimblehq.surveys.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val encryptedUserDatastore: EncryptedUserDatastore,
) : UserRepository {

    override suspend fun fetchUser() {
        val userResponse = authApiService.getUser().data
        val userModel = userResponse?.toUserModel()
        if (userModel != null) {
            encryptedUserDatastore.saveUser(userModel)
        }
    }

    override fun getUser(): Flow<UserModel> {
        return encryptedUserDatastore.getUser()
    }
}