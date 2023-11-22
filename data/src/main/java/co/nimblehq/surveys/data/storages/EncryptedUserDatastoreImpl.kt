package co.nimblehq.surveys.data.storages

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Serializer
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import co.nimblehq.surveys.data.storages.datastore.EncryptedUserDatastore
import co.nimblehq.surveys.data.storages.datastore.UserData
import co.nimblehq.surveys.domain.models.user.UserModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.osipxd.security.crypto.createEncrypted
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EncryptedUserDatastoreImpl @Inject constructor(
    @ApplicationContext context: Context
) : EncryptedUserDatastore {

    companion object {
        private const val FILE_NAME = "encrypted_user_data"
    }

    private val dataStore = DataStoreFactory.createEncrypted(UserPreferencesSerializer) {
        EncryptedFile.Builder(
            context.dataStoreFile(FILE_NAME),
            context,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()
    }

    override suspend fun saveUser(userModel: UserModel) {
        dataStore.updateData { userData ->
            userData.toBuilder()
                .setId(userModel.id)
                .setName(userModel.name)
                .setEmail(userModel.email)
                .setAvatarUrl(userModel.avatarUrl)
                .build()
        }
    }

    override fun getUser(): Flow<UserModel> {
        return dataStore.data.map { userData -> userData.toUserModel() }
    }

    override suspend fun clearAll() {
        dataStore.updateData { userData ->
            userData.toBuilder().clear().build()
        }
    }

    private fun UserData.toUserModel(): UserModel = UserModel(
        id = id,
        name = name,
        email = email,
        avatarUrl = avatarUrl,
    )

    private object UserPreferencesSerializer : Serializer<UserData> {
        override val defaultValue: UserData = UserData.getDefaultInstance()
        override suspend fun readFrom(input: InputStream): UserData {
            try {
                return UserData.parseFrom(input)
            } catch (exception: InvalidProtocolBufferException) {
                throw CorruptionException("Cannot read proto.", exception)
            }
        }

        override suspend fun writeTo(t: UserData, output: OutputStream) = t.writeTo(output)
    }
}